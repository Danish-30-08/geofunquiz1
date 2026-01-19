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
fun ProfileScreen(
    xp: Int, 
    rank: Int = 0,
    displayName: String = "Junior Explorer",
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(4.dp, Color(0xFF3B82F6), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color(0xFF3B82F6)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = displayName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1E293B)
        )
        Text(
            text = "Level 5 • Map Maestro",
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileStatCard(
                label = "TOTAL XP",
                value = xp.toString(),
                modifier = Modifier.weight(1f)
            )
            ProfileStatCard(
                label = "CURRENT RANK",
                value = if (rank > 0) "#$rank" else "N/A",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Your Badge Collection",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1E293B)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BadgeItem(label = "Starter", isLocked = xp < 100, modifier = Modifier.weight(1f))
                BadgeItem(label = "Explorer", isLocked = xp < 500, modifier = Modifier.weight(1f))
                BadgeItem(label = "Master", isLocked = xp < 1000, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BadgeItem(label = "Elite", isLocked = xp < 2000, modifier = Modifier.weight(1f))
                BadgeItem(label = "Legend", isLocked = xp < 5000, modifier = Modifier.weight(1f))
                BadgeItem(label = "King", isLocked = xp < 10000, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- 5. Butang Leave Game ---
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(Icons.Rounded.Logout, contentDescription = null, tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Leave Game", color = Color.Red, fontWeight = FontWeight.Black)
        }

        Spacer(modifier = Modifier.height(100.dp))
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
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(value, fontSize = 26.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E293B))
        }
    }
}

@Composable
fun BadgeItem(label: String, isLocked: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(
                width = 2.dp,
                color = if (isLocked) Color(0xFFE2E8F0) else Color(0xFF3B82F6),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if (isLocked) Icons.Rounded.Lock else Icons.Rounded.EmojiEvents,
                contentDescription = null,
                tint = if (isLocked) Color(0xFFCBD5E1) else Color(0xFFFFD700),
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = if (isLocked) "Locked" else label,
                fontSize = 11.sp,
                color = if (isLocked) Color(0xFF94A3B8) else Color(0xFF3B82F6),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
