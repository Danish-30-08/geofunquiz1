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
            // Mengambil state terkini (termasuk XP) daripada ViewModel
            val authState by authViewModel.ui.collectAsState()

            // State untuk navigasi tab bawah
            var currentTab by remember { mutableStateOf("home") }
            // State untuk pertukaran antara skrin utama dan skrin kuiz
            var currentScreen by remember { mutableStateOf("main") }

            // Simpan skor sementara untuk paparan skrin skor
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
                    // innerPadding mengelakkan kandungan bertindih dengan BottomBar
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentTab) {
                            "home" -> {
                                JuniorExplorerScreen(
                                    xp = authState.xp, // Data XP daripada database
                                    onStartQuiz = { currentScreen = "quiz" },
                                    onStartCapitalsQuiz = { currentScreen = "capitals_quiz" },
                                    onLogout = { handleLogout() }
                                )
                            }
                            "explore" -> {
                                ExploreScreen()
                            }
                            "rank" -> {
                                RankScreen()
                            }
                            "profile" -> {
                                // Menghantar data XP ke ProfileScreen anda
                                ProfileScreen(
                                    xp = authState.xp,
                                    onLogout = { handleLogout() }
                                )
                            }
                            else -> {
                                Box {}
                            }
                        }
                    }
                }
            } else {
                // Logik untuk skrin Kuiz dan Skor (Fullscreen)
                when (currentScreen) {
                    "quiz" -> {
                        QuizScreen(
                            onFinish = { score, total ->
                                finalScore = score
                                totalQuestions = total
                                currentScreen = "score"
                                // Simpan skor ke Firebase
                                authViewModel.saveQuizScore(score)
                            }
                        )
                    }
                    "capitals_quiz" -> {
                        CapitalsQuizScreen(
                            onFinish = { score, total ->
                                finalScore = score
                                totalQuestions = total
                                currentScreen = "score"
                                // Simpan skor ke Firebase
                                authViewModel.saveQuizScore(score)
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

    /**
     * Fungsi untuk menguruskan proses log keluar (Google & Firebase)
     */
    private fun handleLogout() {
        authViewModel.logout() // Sign out daripada Firebase

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(this, gso).signOut() // Sign out daripada Google Account

        // Kembali ke skrin Login dan kosongkan 'back stack'
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}