package com.example.geofunquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.geofunquiz.ui.theme.BottomNavigationBar
import com.example.geofunquiz.ui.theme.JuniorExplorerScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // State to handle main navigation
            var currentTab by remember { mutableStateOf("home") }
            var currentScreen by remember { mutableStateOf("main") }
            
            // Quiz results state
            var finalScore by remember { mutableIntStateOf(0) }
            var totalQuestions by remember { mutableIntStateOf(0) }

            if (currentScreen == "main") {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = currentTab,
                            onTabSelected = { currentTab = it }
                        )
                    }
                ) { innerPadding ->
                    // Correctly use innerPadding to avoid content overlapping with BottomBar
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentTab) {
                            "home" -> {
                                JuniorExplorerScreen(
                                    onStartQuiz = { currentScreen = "quiz" },
                                    onStartCapitalsQuiz = { currentScreen = "capitals_quiz" },
                                    onLogout = { handleLogout() }
                                )
                            }
                            "explore" -> {
                                ExploreScreen()
                            }
                            else -> {
                                // Default content
                                Box {}
                            }
                        }
                    }
                }
            } else {
                // Handle Quiz and Score screens (fullscreen)
                when (currentScreen) {
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
                            onPlayAgain = { currentScreen = "main"; currentTab = "home" },
                            onGoHome = { currentScreen = "main"; currentTab = "home" }
                        )
                    }
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
