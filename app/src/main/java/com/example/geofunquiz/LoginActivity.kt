package com.example.geofunquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private val vm: AuthViewModel by viewModels()
    private lateinit var googleClient: GoogleSignInClient

    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    vm.loginWithGoogleAccount(account)
                } catch (e: Exception) {
                    Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Google client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(this, gso)

        // receive email prefill from signup
        val prefillEmail = intent.getStringExtra("prefill_email") ?: ""
        if (prefillEmail.isNotBlank()) vm.setEmail(prefillEmail)

        lifecycleScope.launch {
            vm.event.collect { ev ->
                when (ev) {
                    is AuthEvent.Toast -> Toast.makeText(this@LoginActivity, ev.msg, Toast.LENGTH_LONG).show()
                    is AuthEvent.NavigateToMain -> {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    is AuthEvent.NavigateToLogin -> { /* already here */ }
                }
            }
        }

        setContent {
            val state by vm.ui.collectAsState()

            LoginScreen(
                email = state.email,
                password = state.password,
                loading = state.loading,
                onEmailChange = vm::setEmail,
                onPasswordChange = vm::setPassword,
                onLoginClick = { vm.loginEmailPassword(state.email, state.password) },
                onGoogleClick = { googleLauncher.launch(googleClient.signInIntent) },
                onSignUpClick = { startActivity(Intent(this, SignUpActivity::class.java)) },
                onForgotPassword = { vm.resetPassword(state.email) }
            )
        }
    }
}

@Composable
fun LoginScreen(
    email: String,
    password: String,
    loading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPassword: () -> Unit
) {
    val focus = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE3F0FF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.AccountCircle, null, Modifier.size(64.dp), Color(0xFF3B82F6))
            Spacer(Modifier.height(12.dp))

            Text("Welcome Back, Explorer!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Sign in to find your treasures", fontSize = 14.sp, color = Color.Gray)

            Spacer(Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(20.dp)) {

                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        placeholder = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        placeholder = { Text("Secret Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(
                            text = "Forgot Password?",
                            fontSize = 12.sp,
                            color = Color(0xFF2563EB),
                            modifier = Modifier.clickable(enabled = !loading) { onForgotPassword() }
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = { focus.clearFocus(); onLoginClick() },
                        enabled = !loading,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E))
                    ) {
                        if (loading) {
                            CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
                            Spacer(Modifier.width(10.dp))
                            Text("Signing in...", fontWeight = FontWeight.Bold)
                        } else {
                            Text("Go!", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("OR", color = Color.Gray)
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { focus.clearFocus(); onGoogleClick() },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
            ) {
                Text("Continue with Google", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            Row {
                Text("New here? ")
                Text(
                    "Join Now!",
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(enabled = !loading) { onSignUpClick() }
                )
            }
        }
    }
}
