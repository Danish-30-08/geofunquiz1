package com.example.geofunquiz

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthUiState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val xp: Int = 0,
    val rank: Int = 0,
    val displayName: String = "Junior Explorer",
    val isSavingScore: Boolean = false
)

sealed class AuthEvent {
    data class Toast(val msg: String) : AuthEvent()
    data class NavigateToLogin(val prefillEmail: String = "") : AuthEvent()
    object NavigateToMain : AuthEvent()
}

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _ui = MutableStateFlow(AuthUiState())
    val ui = _ui.asStateFlow()

    private val _event = MutableSharedFlow<AuthEvent>()
    val event = _event.asSharedFlow()

    init {
        // Listen to auth state changes to load user data
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val extractedName = user.email?.substringBefore("@") ?: "Junior Explorer"
                _ui.value = _ui.value.copy(displayName = extractedName)
                fetchUserData(user.uid, user.email)
            } else {
                _ui.value = _ui.value.copy(xp = 0, rank = 0, displayName = "Junior Explorer")
            }
        }
    }

    private fun fetchUserData(uid: String, email: String?) {
        viewModelScope.launch {
            try {
                val doc = db.collection("users").document(uid).get().await()
                if (doc.exists()) {
                    val xp = doc.getLong("xp")?.toInt() ?: 0
                    _ui.value = _ui.value.copy(xp = xp)
                    // Update email in Firestore if missing
                    if (email != null && doc.getString("email") == null) {
                        db.collection("users").document(uid).update("email", email)
                    }
                    calculateRank(xp, uid)
                } else {
                    // Create initial user doc if it doesn't exist
                    val data = mutableMapOf<String, Any>("xp" to 0)
                    if (email != null) data["email"] = email
                    db.collection("users").document(uid).set(data).await()
                    _ui.value = _ui.value.copy(xp = 0, rank = 0)
                }
            } catch (e: Exception) {
                // Silently fail or log
            }
        }
    }

    private fun calculateRank(userXp: Int, uid: String) {
        viewModelScope.launch {
            try {
                // 1. Count users with strictly more XP
                val moreXpCount = db.collection("users")
                    .whereGreaterThan("xp", userXp)
                    .count()
                    .get(AggregateSource.SERVER).await().count
                
                // 2. Count users with the same XP but a lower ID (tie-break)
                val sameXpCount = db.collection("users")
                    .whereEqualTo("xp", userXp)
                    .whereLessThan(FieldPath.documentId(), uid)
                    .count()
                    .get(AggregateSource.SERVER).await().count
                
                val finalRank = moreXpCount + sameXpCount + 1
                _ui.value = _ui.value.copy(rank = finalRank.toInt())
            } catch (e: Exception) {
                // Fail silently
                e.printStackTrace()
            }
        }
    }

    fun setEmail(v: String) { _ui.value = _ui.value.copy(email = v) }
    fun setPassword(v: String) { _ui.value = _ui.value.copy(password = v) }

    fun signUpEmailPassword(emailRaw: String, password: String) {
        val email = emailRaw.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emitToast("Please enter a valid email")
            return
        }
        if (password.length < 6) {
            emitToast("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            setLoading(true)
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    // Create user profile in Firestore
                    db.collection("users").document(user.uid).set(mapOf(
                        "xp" to 0,
                        "email" to email
                    )).await()
                }
                emitToast("Registration successful!")
                _event.emit(AuthEvent.NavigateToMain)
            } catch (e: Exception) {
                emitToast(e.message ?: "Sign up failed")
            } finally {
                setLoading(false)
            }
        }
    }

    fun loginEmailPassword(emailRaw: String, password: String) {
        val email = emailRaw.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emitToast("Please enter a valid email")
            return
        }
        if (password.isBlank()) {
            emitToast("Please enter your password")
            return
        }

        viewModelScope.launch {
            setLoading(true)
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _event.emit(AuthEvent.NavigateToMain)
            } catch (e: Exception) {
                emitToast(e.message ?: "Login failed")
            } finally {
                setLoading(false)
            }
        }
    }

    fun resetPassword(emailRaw: String) {
        val email = emailRaw.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emitToast("Please enter a valid email first")
            return
        }
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
                emitToast("Password reset email sent")
            } catch (e: Exception) {
                emitToast(e.message ?: "Reset failed")
            }
        }
    }

    fun saveQuizScore(score: Int) {
        val user = auth.currentUser ?: return
        viewModelScope.launch {
            _ui.value = _ui.value.copy(isSavingScore = true)
            try {
                val xpToAdd = (score * 50).toLong()
                db.collection("users").document(user.uid)
                    .update("xp", FieldValue.increment(xpToAdd)).await()
                
                // Refresh local XP
                val doc = db.collection("users").document(user.uid).get().await()
                val updatedXp = doc.getLong("xp")?.toInt() ?: 0
                _ui.value = _ui.value.copy(xp = updatedXp)
                
                // Recalculate Rank
                calculateRank(updatedXp, user.uid)
                
                emitToast("+$xpToAdd XP earned!")
            } catch (e: Exception) {
                emitToast("Failed to save score: ${e.message}")
            } finally {
                _ui.value = _ui.value.copy(isSavingScore = false)
            }
        }
    }

    fun loginWithGoogleAccount(account: GoogleSignInAccount?) {
        if (account == null) {
            emitToast("Google sign-in failed")
            return
        }
        val token = account.idToken
        if (token.isNullOrBlank()) {
            emitToast("Missing Google token")
            return
        }

        viewModelScope.launch {
            setLoading(true)
            try {
                val credential = GoogleAuthProvider.getCredential(token, null)
                val result = auth.signInWithCredential(credential).await()
                val user = result.user
                if (user != null) {
                    // Check if user exists in Firestore, if not create
                    val doc = db.collection("users").document(user.uid).get().await()
                    val data = mutableMapOf<String, Any>()
                    if (!doc.exists()) {
                        data["xp"] = 0
                    }
                    if (user.email != null) {
                        data["email"] = user.email!!
                    }
                    if (data.isNotEmpty()) {
                        db.collection("users").document(user.uid).set(data, SetOptions.merge()).await()
                    }
                }
                _event.emit(AuthEvent.NavigateToMain)
            } catch (e: Exception) {
                emitToast(e.message ?: "Google login failed")
            } finally {
                setLoading(false)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            auth.signOut()
            _event.emit(AuthEvent.NavigateToLogin())
        }
    }

    private fun setLoading(v: Boolean) {
        _ui.value = _ui.value.copy(loading = v)
    }

    private fun emitToast(msg: String) {
        viewModelScope.launch { _event.emit(AuthEvent.Toast(msg)) }
    }
}
