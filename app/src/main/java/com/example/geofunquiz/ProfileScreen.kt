package com.example.geofunquiz

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6)) // Warna kuning krim sama dengan Home
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Gambar Profil (Bulat)
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(4.dp, Color(0xFF3B82F6), CircleShape) // Biru
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Person, contentDescription = null, modifier = Modifier.size(60.dp), tint = Color(0xFF3B82F6))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Junior Explorer", fontSize = 28.sp, fontWeight = FontWeight.Black)
        Text("Level 5 • Map Maestro", color = Color.Gray, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // 2. XP & Rank Row
        Row(modifier = Modifier.fillMaxWidth()) {
            ProfileStatCard("TOTAL XP", "1200", Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            ProfileStatCard("CURRENT RANK", "#124", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. Badge Collection Title
        Text(
            "Your Badge Collection",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )

        // Grid Badge (3 lajur)
        Spacer(modifier = Modifier.height(16.dp))
        repeat(2) { // 2 Baris
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(3) { // 3 Item sembaris
                    Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                        LockedBadgeItem()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 4. Butang Leave Game
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(Icons.Rounded.Logout, contentDescription = null, tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Leave Game", color = Color.Red, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun ProfileStatCard(label: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E293B))
        }
    }
}

@Composable
fun LockedBadgeItem() {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(2.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Rounded.Lock, contentDescription = null, tint = Color.LightGray)
            Text("Locked", fontSize = 10.sp, color = Color.LightGray)
        }
    }
}