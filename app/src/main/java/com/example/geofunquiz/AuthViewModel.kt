package com.example.geofunquiz

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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
                fetchUserData(user.uid)
            } else {
                _ui.value = _ui.value.copy(xp = 0)
            }
        }
    }

    private fun fetchUserData(uid: String) {
        viewModelScope.launch {
            try {
                val doc = db.collection("users").document(uid).get().await()
                if (doc.exists()) {
                    val xp = doc.getLong("xp")?.toInt() ?: 0
                    _ui.value = _ui.value.copy(xp = xp)
                } else {
                    // Create initial user doc if it doesn't exist
                    db.collection("users").document(uid).set(mapOf("xp" to 0)).await()
                    _ui.value = _ui.value.copy(xp = 0)
                }
            } catch (e: Exception) {
                // Silently fail or log
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
                    db.collection("users").document(user.uid).set(mapOf("xp" to 0)).await()
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
                    if (!doc.exists()) {
                        db.collection("users").document(user.uid).set(mapOf("xp" to 0)).await()
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
