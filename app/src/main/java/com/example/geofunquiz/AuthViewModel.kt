package com.example.geofunquiz

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthUiState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = ""
)

sealed class AuthEvent {
    data class Toast(val msg: String) : AuthEvent()
    data class NavigateToLogin(val prefillEmail: String = "") : AuthEvent()
    object NavigateToMain : AuthEvent()
}

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _ui = MutableStateFlow(AuthUiState())
    val ui = _ui.asStateFlow()

    private val _event = MutableSharedFlow<AuthEvent>()
    val event = _event.asSharedFlow()

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
                auth.createUserWithEmailAndPassword(email, password).await()
                emitToast("Registration successful!")
                
                // Immediately navigate to Main instead of requiring verification
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
                
                val user = auth.currentUser
                if (user == null) {
                    emitToast("Login failed")
                    return@launch
                }

                // Removed email verification check
                _event.emit(AuthEvent.NavigateToMain)
            } catch (e: Exception) {
                emitToast(e.message ?: "Login failed")
            } finally {
                setLoading(false)
            }
        }
    }

    fun resendVerification() {
        emitToast("Email verification is disabled.")
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

    fun loginWithGoogleAccount(account: GoogleSignInAccount?) {
        if (account == null) {
            emitToast("Google sign-in failed")
            return
        }
        val token = account.idToken
        if (token.isNullOrBlank()) {
            emitToast("Missing Google token. Check Web Client ID / SHA-1")
            return
        }

        viewModelScope.launch {
            setLoading(true)
            try {
                val credential = GoogleAuthProvider.getCredential(token, null)
                auth.signInWithCredential(credential).await()
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
