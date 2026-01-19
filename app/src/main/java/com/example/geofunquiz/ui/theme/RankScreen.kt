package com.example.geofunquiz.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geofunquiz.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

data class Explorer(
    val id: String = "",
    val name: String = "Explorer",
    val xp: Int = 0,
    val avatarIcon: ImageVector = Icons.Rounded.Person,
    val color: Color = Color(0xFFE2E8F0),
    val isCurrentUser: Boolean = false
)

@Composable
fun RankScreen(authViewModel: AuthViewModel) {
    val authState by authViewModel.ui.collectAsState()
    var rankingList by remember { mutableStateOf<List<Explorer>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    val currentUserId = remember { FirebaseAuth.getInstance().currentUser?.uid }

    LaunchedEffect(authState.xp) {
        isLoading = true
        val db = FirebaseFirestore.getInstance()
        try {
            // Simplified query to avoid "Missing Index" errors
            val snapshot = db.collection("users")
                .orderBy("xp", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .await()
            
            rankingList = snapshot.documents.mapIndexed { index, doc ->
                val email = doc.getString("email") ?: ""
                val rawName = if (email.contains("@")) email.substringBefore("@") else "Explorer_${doc.id.take(4)}"
                val isMe = doc.id == currentUserId
                
                Explorer(
                    id = doc.id,
                    name = rawName,
                    xp = doc.getLong("xp")?.toInt() ?: 0,
                    isCurrentUser = isMe,
                    avatarIcon = when(index) {
                        0 -> Icons.Rounded.Face
                        1 -> Icons.Rounded.AirplanemodeActive
                        2 -> Icons.Rounded.Map
                        3 -> Icons.Rounded.Explore
                        else -> Icons.Rounded.Public
                    },
                    color = when(index) {
                        0 -> Color(0xFFFFD700)
                        1 -> Color(0xFF3B82F6)
                        2 -> Color(0xFF4ADE80)
                        else -> Color(0xFFE2E8F0)
                    }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        containerColor = Color(0xFFFFFBE6)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Top Explorers!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B)
            )
            Text(
                text = "See who's winning this week!",
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (isLoading && rankingList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF3B82F6))
                }
            } else if (rankingList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No explorers found yet!", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    if (rankingList.size > 1) {
                        PodiumItem(rankingList[1], "#2", Color(0xFF3B82F6))
                    }
                    if (rankingList.isNotEmpty()) {
                        PodiumItem(rankingList[0], "#1", Color(0xFFFFD700), isLarge = true)
                    }
                    if (rankingList.size > 2) {
                        PodiumItem(rankingList[2], "#3", Color(0xFF4ADE80))
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    itemsIndexed(rankingList.drop(3)) { index, explorer ->
                        RankListItem(explorer, index + 4)
                    }
                }
            }
        }
    }
}

@Composable
fun PodiumItem(explorer: Explorer, rank: String, color: Color, isLarge: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(if (isLarge) 100.dp else 80.dp)
                    .shadow(10.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (explorer.isCurrentUser) 4.dp else 2.dp,
                        color = if (explorer.isCurrentUser) Color(0xFF2563EB) else Color.White.copy(alpha = 0.5f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = explorer.avatarIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(if (isLarge) 50.dp else 40.dp)
                )
            }
            
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 12.dp),
                shape = CircleShape,
                color = Color(0xFF2563EB),
                shadowElevation = 4.dp
            ) {
                Text(
                    text = rank,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (explorer.isCurrentUser) "${explorer.name} (YOU)" else explorer.name,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp,
            color = if (explorer.isCurrentUser) Color(0xFF2563EB) else Color(0xFF1E293B),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            text = "${explorer.xp} XP",
            fontWeight = FontWeight.Black,
            fontSize = 14.sp,
            color = if(rank == "#1") Color(0xFFD4AF37) else color,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RankListItem(explorer: Explorer, rank: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (explorer.isCurrentUser) Color(0xFFE3F2FD) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rank.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B),
                modifier = Modifier.width(30.dp)
            )

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(if (explorer.isCurrentUser) Color(0xFF3B82F6) else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = explorer.avatarIcon,
                    contentDescription = null,
                    tint = if (explorer.isCurrentUser) Color.White else Color(0xFF64748B),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = if (explorer.isCurrentUser) "${explorer.name} (YOU)" else explorer.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (explorer.isCurrentUser) Color(0xFF2563EB) else Color(0xFF1E293B),
                modifier = Modifier.weight(1f),
                maxLines = 1
            )

            Text(
                text = "${explorer.xp} XP",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = Color(0xFF3B82F6)
            )
        }
    }
}
