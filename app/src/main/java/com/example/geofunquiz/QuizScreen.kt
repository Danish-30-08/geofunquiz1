package com.example.geofunquiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Question(
    val id: Int,
    val text: String,
    val flagCode: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

@Composable
fun QuizScreen(onBack: () -> Unit = {}) {
    val questions = remember {
        listOf(
            Question(1, "Which country does this flag belong to?", "BR", listOf("Brazil", "Argentina", "Portugal", "Spain"), 0),
            Question(2, "Identify this flag:", "JP", listOf("South Korea", "China", "Japan", "Vietnam"), 2),
            // Add more questions here
        )
    }

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf(-1) }
    var isAnswered by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentQuestionIndex]
    val progress = (currentQuestionIndex + 1).toFloat() / questions.size

    Scaffold(
        containerColor = Color(0xFFF0F7FF) // Light blue background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF22C55E), // Green progress
                trackColor = Color(0xFFD1E4FF)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Header: Question Number and Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${currentQuestionIndex + 1}/${questions.size}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF334155)
                )

                Surface(
                    color = Color(0xFFD1E4FF),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "FLAGS QUIZ",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Question Text
            Text(
                text = currentQuestion.text,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Flag Card (Placeholder for now)
            Card(
                modifier = Modifier
                    .size(240.dp, 160.dp)
                    .border(2.dp, Color(0xFFD1E4FF), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = currentQuestion.flagCode,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Options
            currentQuestion.options.forEachIndexed { index, option ->
                OptionItem(
                    text = option,
                    isSelected = selectedOptionIndex == index,
                    isCorrect = currentQuestion.correctAnswerIndex == index,
                    isAnswered = isAnswered,
                    onClick = {
                        if (!isAnswered) {
                            selectedOptionIndex = index
                            isAnswered = true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Next Button
            if (isAnswered) {
                Button(
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedOptionIndex = -1
                            isAnswered = false
                        } else {
                            // Quiz Finished!
                            onBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF84CC16), Color(0xFF22C55E))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentQuestionIndex < questions.size - 1) "Next Question" else "Finish",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionItem(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isAnswered && isCorrect -> Color(0xFF22C55E)
            isSelected && isAnswered && !isCorrect -> Color(0xFFEF4444)
            isSelected -> Color(0xFFD1E4FF)
            else -> Color.White
        },
        label = "bgColor"
    )

    val contentColor = if (isAnswered && (isCorrect || isSelected)) Color.White else Color(0xFF334155)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isAnswered) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 0.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )

            if (isAnswered && isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizScreen()
}
