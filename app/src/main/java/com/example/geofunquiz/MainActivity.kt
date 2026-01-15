package com.example.geofunquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.example.geofunquiz.ui.theme.JuniorExplorerScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // State to handle screen navigation
            var currentScreen by remember { mutableStateOf("home") }
            var finalScore by remember { mutableIntStateOf(0) }
            var totalQuestions by remember { mutableIntStateOf(0) }

            when (currentScreen) {
                "home" -> {
                    JuniorExplorerScreen(
                        onStartQuiz = { currentScreen = "quiz" },
                        onStartCapitalsQuiz = { currentScreen = "capitals_quiz" },
                        onLogout = { handleLogout() }
                    )
                }
                "quiz" -> {
                    QuizScreen(
                        onFinish = { score, total ->
                            finalScore = score
                            totalQuestions = total
                            currentScreen = "score"
                        }
                    )
                }
                "capitals_quiz" -> {
                    CapitalsQuizScreen(
                        onFinish = { score, total ->
                            finalScore = score
                            totalQuestions = total
                            currentScreen = "score"
                        }
                    )
                }
                "score" -> {
                    ScoreScreen(
                        score = finalScore,
                        totalQuestions = totalQuestions,
                        onPlayAgain = { currentScreen = "home" },
                        onGoHome = { currentScreen = "home" }
                    )
                }
            }
        }
    }

    private fun handleLogout() {
        authViewModel.logout()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(this, gso).signOut()

        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
