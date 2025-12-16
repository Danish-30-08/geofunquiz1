package com.example.geofunquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {

    val backgroundColor = Color(0xFFE3F0FF)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

            // 👤 Icon
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color(0xFF3B82F6),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome Back, Explorer!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )

            Text(
                text = "Sign in to find your treasures",
                fontSize = 14.sp,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🧾 Login Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        placeholder = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        placeholder = { Text("Secret Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Forgot Password?",
                        color = Color(0xFF2563EB),
                        fontSize = 13.sp,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Go Button
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF22C55E)
                        )
                    ) {
                        Text(
                            text = "Go!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // OR Divider
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.width(80.dp))
                Text(
                    text = " OR ",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Divider(modifier = Modifier.width(80.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Google Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                )
            ) {
                Text(
                    text = "Continue with Google",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Register
            Row {
                Text("New here? ")
                Text(
                    text = "Join Now!",
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}
