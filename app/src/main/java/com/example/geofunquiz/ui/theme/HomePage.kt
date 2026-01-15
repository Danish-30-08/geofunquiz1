package com.example.geofunquiz.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Custom Colors
val BackgroundColor = Color(0xFFFFF9E6)
val OrangeGradientStart = Color(0xFFFF8C42)
val OrangeGradientEnd = Color(0xFFF05D5E)
val BlueIcon = Color(0xFF42A5F5)
val GreenIcon = Color(0xFF66BB6A)
val OrangeIcon = Color(0xFFFFA726)

@Composable
fun JuniorExplorerScreen(
    onStartQuiz: () -> Unit = {},
    onStartCapitalsQuiz: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        bottomBar = { BottomNavigationBar() },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            HeaderSection(onLogout = onLogout)
            Spacer(modifier = Modifier.height(24.dp))
            BonusLevelCard(onStartQuiz = onStartCapitalsQuiz)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Choose Your Mission",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(16.dp))
            MissionCard(
                icon = Icons.Rounded.Public,
                iconColor = BlueIcon,
                iconBg = Color(0xFFE3F2FD),
                title = "Flag Detective",
                subtitle = "Guess the country flag",
                playButtonColor = Color(0xFF4285F4),
                onPlayClick = onStartQuiz
            )
            Spacer(modifier = Modifier.height(16.dp))
            MissionCard(
                icon = Icons.Rounded.LocationOn,
                iconColor = GreenIcon,
                iconBg = Color(0xFFE8F5E9),
                title = "Capital City Finder",
                subtitle = "Match capitals to countries",
                playButtonColor = Color(0xFF34A853),
                onPlayClick = onStartCapitalsQuiz
            )
            Spacer(modifier = Modifier.height(16.dp))
            MissionCard(
                icon = Icons.Rounded.StarOutline,
                iconColor = OrangeIcon,
                iconBg = Color(0xFFFFF3E0),
                title = "Fun Facts Trivia",
                subtitle = "Trivia about culture & history",
                playButtonColor = Color(0xFFFBBC05)
            )
        }
    }
}

@Composable
fun HeaderSection(onLogout: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(text = "Hi there,", fontSize = 16.sp, color = Color.Gray)
            Text(
                text = "Junior Explorer!",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF333333)
            )
            Text(text = "🤩", fontSize = 32.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🎗️", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "1200", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text(text = "XP", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onLogout) {
                Text("Logout", color = Color(0xFFF05D5E), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun BonusLevelCard(onStartQuiz: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth().height(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(listOf(OrangeGradientStart, OrangeGradientEnd)))
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.EmojiEvents,
                contentDescription = null,
                modifier = Modifier.size(120.dp).align(Alignment.TopEnd).offset(x = 30.dp, y = (-20).dp),
                tint = Color.White.copy(alpha = 0.2f)
            )

            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                Surface(color = Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp)) {
                    Text(
                        text = "BONUS LEVEL",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Column {
                    Text(text = "Capital Cities Blitz", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Earn 2x XP before the clock runs out!", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                }

                Button(
                    onClick = onStartQuiz,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Start Quest", color = OrangeGradientEnd, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun MissionCard(
    icon: ImageVector,
    iconColor: Color,
    iconBg: Color,
    title: String,
    subtitle: String,
    playButtonColor: Color,
    onPlayClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)).background(iconBg), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(
                onClick = onPlayClick,
                modifier = Modifier.size(40.dp).background(playButtonColor, CircleShape)
            ) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Rounded.Home, null) }, label = { Text("Home") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Rounded.Search, null) }, label = { Text("Explore") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.AutoMirrored.Rounded.TrendingUp, null) }, label = { Text("Rank") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Rounded.PersonOutline, null) }, label = { Text("Profile") })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JuniorExplorerScreen()
}
