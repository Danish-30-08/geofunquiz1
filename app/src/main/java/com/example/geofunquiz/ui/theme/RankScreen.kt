package com.example.geofunquiz.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Explorer(
    val rank: Int,
    val name: String,
    val xp: Int,
    val avatarColor: Color,
    val isTop3: Boolean = false
)

val explorers = listOf(
    Explorer(1, "GeoMas...", 2450, Color(0xFFFFD700), isTop3 = true),
    Explorer(2, "TravelB...", 2100, Color(0xFFC0C0C0), isTop3 = true),
    Explorer(3, "MapNord", 1850, Color(0xFFCD7F32), isTop3 = true),
    Explorer(4, "Explorer Jane", 1000, Color(0xFFB0E0E6)),
    Explorer(5, "CaptainPlanet", 1200, Color(0xFF98FB98))
)

@Composable
fun RankScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBE6))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Top Explorers!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See who's winning this week!",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Top3Podium(explorers.filter { it.isTop3 })
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(explorers.filter { !it.isTop3 }) { index, explorer ->
                    ExplorerListItem(explorer = explorer, rank = index + 4)
                }
            }
        }
    }
}

@Composable
fun Top3Podium(topExplorers: List<Explorer>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        val sortedExplorers = topExplorers.sortedBy { it.rank }
        if (sortedExplorers.size > 1) {
            PodiumItem(explorer = sortedExplorers[1], height = 100.dp)
        }
        if (sortedExplorers.isNotEmpty()) {
            PodiumItem(explorer = sortedExplorers[0], height = 120.dp, isCenter = true)
        }
        if (sortedExplorers.size > 2) {
            PodiumItem(explorer = sortedExplorers[2], height = 100.dp)
        }
    }
}

@Composable
fun PodiumItem(explorer: Explorer, height: androidx.compose.ui.unit.Dp, isCenter: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (isCenter) 80.dp else 70.dp)
                .clip(CircleShape)
                .background(explorer.avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = explorer.name.first().toString(), fontSize = 32.sp, color = Color.White)
        }
        Text(text = explorer.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = "${explorer.xp} XP", color = Color.Gray, fontSize = 14.sp)
        Box(
            modifier = Modifier
                .height(height)
                .width(80.dp)
                .background(
                    color = explorer.avatarColor.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            Text(
                text = "#${explorer.rank}",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun ExplorerListItem(explorer: Explorer, rank: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$rank", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(explorer.avatarColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = explorer.name.first().toString(), fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = explorer.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "${explorer.xp} XP", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankScreenPreview() {
    RankScreen()
}
