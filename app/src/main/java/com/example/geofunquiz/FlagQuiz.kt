package com.example.geofunquiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Question(
    val id: Int,
    val text: String,
    val flagCode: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

@Composable
fun QuizScreen(onFinish: (Int, Int) -> Unit) {
    val questions = remember {
        listOf(
            Question(1, "Which country does this flag belong to?", "BR", listOf("Brazil", "Argentina", "Portugal", "Spain"), 0),
            Question(2, "Identify this flag:", "JP", listOf("South Korea", "China", "Japan", "Vietnam"), 2),
            Question(3, "Which country uses this flag?", "ID", listOf("Monaco", "Poland", "Indonesia", "Singapore"), 2),
            Question(4, "This flag belongs to:", "US", listOf("United Kingdom", "Australia", "USA", "Liberia"), 2),
            Question(5, "Name this country's flag:", "FR", listOf("Italy", "France", "Netherlands", "Russia"), 1),
            Question(6, "Identify this flag:", "DE", listOf("Belgium", "Germany", "Austria", "Switzerland"), 1),
            Question(7, "Which country does this flag belong to?", "IT", listOf("Ireland", "Spain", "Italy", "Mexico"), 2),
            Question(8, "This flag belongs to:", "CA", listOf("Canada", "USA", "Turkey", "Switzerland"), 0),
            Question(9, "Identify this flag:", "AU", listOf("New Zealand", "United Kingdom", "Australia", "Fiji"), 2),
            Question(10, "Name this country's flag:", "EG", listOf("Syria", "Iraq", "Egypt", "Yemen"), 2),
            Question(11, "Which country uses this flag?", "ZA", listOf("Namibia", "South Africa", "Zimbabwe", "Kenya"), 1),
            Question(12, "Identify this flag:", "KR", listOf("China", "Japan", "North Korea", "South Korea"), 3),
            Question(13, "This flag belongs to:", "TH", listOf("Thailand", "Vietnam", "Cambodia", "Laos"), 0),
            Question(14, "Which country does this flag belong to?", "ES", listOf("Spain", "Portugal", "Mexico", "Italy"), 0),
            Question(15, "Identify this flag:", "MX", listOf("Italy", "Ireland", "Mexico", "India"), 2)
        )
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableIntStateOf(-1) }
    var isAnswered by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }

    val currentQuestion = questions[currentQuestionIndex]
    val progress = (currentQuestionIndex + 1).toFloat() / questions.size

    Scaffold(
        containerColor = Color(0xFFF0F7FF) // Match light blue background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(CircleShape),
                color = Color(0xFF22C55E), // Vibrant Green
                trackColor = Color(0xFFD1E4FF)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Question Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${currentQuestionIndex + 1}/${questions.size}",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = Color(0xFF1E293B)
                )

                Surface(
                    color = Color(0xFFD1E4FF),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "FLAGS QUIZ",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2563EB)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Question Title
            Text(
                text = currentQuestion.text,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Flag Display Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1.5f)
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(3.dp, Color(0xFFB9D8FF)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = "https://flagcdn.com/w640/${currentQuestion.flagCode.lowercase()}.png",
                        contentDescription = "Flag",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Options List
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                currentQuestion.options.forEachIndexed { index, option ->
                    OptionCard(
                        text = option,
                        isSelected = selectedOptionIndex == index,
                        isCorrect = currentQuestion.correctAnswerIndex == index,
                        isAnswered = isAnswered,
                        onClick = {
                            if (!isAnswered) {
                                selectedOptionIndex = index
                                isAnswered = true
                                if (index == currentQuestion.correctAnswerIndex) {
                                    score++
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Next Question Button
            if (isAnswered) {
                Button(
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedOptionIndex = -1
                            isAnswered = false
                        } else {
                            onFinish(score, questions.size)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
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
                            text = if (currentQuestionIndex < questions.size - 1) "Next Question" else "Finish Quiz",
                            fontSize = 20.sp,
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
fun OptionCard(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    // Determine target colors for background and content
    val targetBgColor = when {
        isAnswered -> when {
            isCorrect -> Color(0xFF22C55E) // Green for correct
            isSelected -> Color(0xFFEF4444) // Red for wrong selection
            else -> Color.White.copy(alpha = 0.5f) // Faded white
        }
        isSelected -> Color(0xFFD1E4FF) // Light blue when clicked before answer
        else -> Color.White
    }

    val targetContentColor = when {
        isAnswered -> if (isCorrect || isSelected) Color.White else Color(0xFF94A3B8)
        else -> Color(0xFF1E293B)
    }

    val animatedBgColor by animateColorAsState(targetValue = targetBgColor, label = "bg")
    val animatedContentColor by animateColorAsState(targetValue = targetContentColor, label = "text")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(
                width = if (isSelected && !isAnswered) 2.dp else 0.dp,
                color = Color(0xFF2563EB),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(if (isSelected && !isAnswered) 0.dp else 4.dp, RoundedCornerShape(16.dp))
            .clickable(enabled = !isAnswered) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = animatedBgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = animatedContentColor
            )

            if (isAnswered && isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Correct",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizInterfacePreview() {
    QuizScreen(onFinish = { _, _ -> })
}
