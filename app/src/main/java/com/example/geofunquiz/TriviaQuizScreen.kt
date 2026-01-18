package com.example.geofunquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TriviaQuizScreen(onFinish: (Int, Int) -> Unit) {
    val questions = TriviaRepository.questions
    var currentIdx by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }

    // State untuk maklum balas
    var showFact by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var selectedAnswerIndex by remember { mutableIntStateOf(-1) }

    val currentQuestion = questions[currentIdx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question ${currentIdx + 1}/${questions.size}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Card Soalan
        Card(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text(
                    text = currentQuestion.question,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Butang Pilihan Jawapan
        currentQuestion.options.forEachIndexed { index, option ->
            val buttonColor = when {
                showFact && index == currentQuestion.correctAnswerIndex -> Color(0xFF4CAF50) // Hijau jika betul
                showFact && index == selectedAnswerIndex && index != currentQuestion.correctAnswerIndex -> Color(0xFFF44336) // Merah jika salah
                else -> Color.White
            }

            Button(
                onClick = {
                    if (!showFact) {
                        selectedAnswerIndex = index
                        if (index == currentQuestion.correctAnswerIndex) {
                            score++
                            isCorrect = true
                        } else {
                            isCorrect = false
                        }
                        showFact = true
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = if (buttonColor == Color.White) Color.Black else Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(2.dp)
            ) {
                Text(text = option, fontWeight = FontWeight.Bold)
            }
        }
    }

    // --- Dialog Maklum Balas & Fun Fact ---
    if (showFact) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    text = if (isCorrect) "CORRECT! 🎉" else "WRONG! ❌",
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.Black
                )
            },
            text = {
                Column {
                    Text(
                        text = "The correct answer is: ${currentQuestion.options[currentQuestion.correctAnswerIndex]}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Did you know?\n${currentQuestion.funFact}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showFact = false
                        selectedAnswerIndex = -1
                        if (currentIdx < questions.size - 1) {
                            currentIdx++
                        } else {
                            // PENTING: Hantar score dan size ke MainActivity
                            onFinish(score, questions.size)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF97316))
                ) {
                    Text("NEXT", fontWeight = FontWeight.Black)
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }
}