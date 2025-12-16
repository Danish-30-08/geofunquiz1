package com.example.geofunquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WelcomeScreen {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}

@Composable
fun WelcomeScreen(onPlayClick: () -> Unit) {

    val backgroundColor = Color(0xFFFFF9DB)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 🌍 Globe Icon
            Icon(
                imageVector = Icons.Default.Public,
                contentDescription = null,
                tint = Color(0xFF2F6BFF),
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // 📝 GeoFun Quiz title
            Text(
                text = buildAnnotatedString {
                    append("Geo")
                    pushStyle(
                        SpanStyle(
                            color = Color(0xFF22C55E),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    append("Fun")
                    pop()
                    append(" Quiz")
                },
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Time to explore the world!",
                fontSize = 16.sp,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // ▶ Button
            Button(
                onClick = onPlayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                ),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Text(
                    text = "Let's Play!  >",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
