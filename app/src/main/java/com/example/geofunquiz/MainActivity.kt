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
import com.example.geofunquiz.ui.theme.RankScreen

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val authState by authViewModel.ui.collectAsState()

            // State navigasi
            var currentTab by remember { mutableStateOf("home") }
            var currentScreen by remember { mutableStateOf("main") }

            // State Skor
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
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentTab) {
                            "home" -> {
                                JuniorExplorerScreen(
                                    xp = authState.xp,
                                    displayName = authState.displayName,
                                    onStartQuiz = { currentScreen = "quiz" },
                                    onStartCapitalsQuiz = { currentScreen = "capitals_quiz" },
                                    onStartTrivia = { currentScreen = "trivia_quiz" },
                                    onLogout = { handleLogout() }
                                )
                            }
                            "explore" -> { 
                                ExploreScreen() 
                            }
                            "rank" -> { 
                                RankScreen(authViewModel = authViewModel) 
                            }
                            "profile" -> {
                                ProfileScreen(
                                    xp = authState.xp,
                                    rank = authState.rank,
                                    displayName = authState.displayName,
                                    onLogout = { handleLogout() }
                                )
                            }
                        }
                    }
                }
            } else {
                // Bahagian Paparan Kuiz Fullscreen
                when (currentScreen) {
                    "quiz" -> {
                        QuizScreen(
                            onFinish = { s, t ->
                                finalScore = s
                                totalQuestions = t
                                authViewModel.saveQuizScore(s)
                                currentScreen = "score"
                            }
                        )
                    }
                    "capitals_quiz" -> {
                        CapitalsQuizScreen(
                            onFinish = { s, t ->
                                finalScore = s
                                totalQuestions = t
                                authViewModel.saveQuizScore(s)
                                currentScreen = "score"
                            }
                        )
                    }
                    "trivia_quiz" -> {
                        TriviaQuizScreen(
                            onFinish = { s, t ->
                                finalScore = s
                                totalQuestions = t
                                authViewModel.saveQuizScore(s)
                                currentScreen = "score"
                            }
                        )
                    }
                    "score" -> {
                        ScoreScreen(
                            score = finalScore,
                            totalQuestions = totalQuestions,
                            onPlayAgain = {
                                currentScreen = "main"
                                currentTab = "home"
                            },
                            onGoHome = {
                                currentScreen = "main"
                                currentTab = "home"
                            }
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
