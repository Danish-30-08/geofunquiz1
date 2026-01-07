package com.example.geofunquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

// 🎨 Custom Colors
val BackgroundColor = Color(0xFFFFF9E6)
val OrangeGradientStart = Color(0xFFFF8C42)
val OrangeGradientEnd = Color(0xFFF05D5E)
val LightBlueBg = Color(0xFFE3F2FD)
val BlueIcon = Color(0xFF42A5F5)
val LightGreenBg = Color(0xFFE8F5E9)
val GreenIcon = Color(0xFF66BB6A)
val LightOrangeBg = Color(0xFFFFF3E0)
val OrangeIcon = Color(0xFFFFA726)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuniorExplorerScreen()
        }
    }
}

@Composable
fun JuniorExplorerScreen() {
    Scaffold(
        bottomBar = { BottomNavigationBar() },
        containerColor = BackgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(24.dp))
            BonusLevelCard()
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
                iconBg = LightBlueBg,
                title = "Flag Detective",
                subtitle = "Guess the country flag",
                playButtonColor = Color(0xFF4285F4)
            )

            Spacer(modifier = Modifier.height(16.dp))

            MissionCard(
                icon = Icons.Rounded.LocationOn,
                iconColor = GreenIcon,
                iconBg = LightGreenBg,
                title = "Capital City Finder",
                subtitle = "Match capitals to countries",
                playButtonColor = Color(0xFF34A853)
            )

            Spacer(modifier = Modifier.height(16.dp))

            MissionCard(
                icon = Icons.Rounded.StarOutline,
                iconColor = OrangeIcon,
                iconBg = LightOrangeBg,
                title = "Fun Facts Trivia",
                subtitle = "Trivia about culture & history",
                playButtonColor = Color(0xFFFBBC05)
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text("Hi there,", fontSize = 16.sp, color = Color.Gray)
            Text(
                "Junior Explorer!",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text("🤩", fontSize = 32.sp)
        }

        Card(
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🎗️", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("1200", fontWeight = FontWeight.Bold)
                    Text("XP", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BonusLevelCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(OrangeGradientStart, OrangeGradientEnd)
                    )
                )
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.EmojiEvents,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 30.dp, y = (-20).dp),
                tint = Color.White.copy(alpha = 0.2f)
            )

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text("BONUS LEVEL", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    "Capital Cities Blitz",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Start Quest", color = OrangeGradientEnd)
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
    playButtonColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconColor)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color.Gray)
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(40.dp)
                    .background(playButtonColor, CircleShape)
            ) {
                Icon(Icons.Filled.PlayArrow, null, tint = Color.White)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(true, {}, { Icon(Icons.Rounded.Home, null) }, label = { Text("Home") })
        NavigationBarItem(false, {}, { Icon(Icons.Rounded.Search, null) }, label = { Text("Explore") })
        NavigationBarItem(false, {}, { Icon(Icons.Rounded.TrendingUp, null) }, label = { Text("Rank") })
        NavigationBarItem(false, {}, { Icon(Icons.Rounded.PersonOutline, null) }, label = { Text("Profile") })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    JuniorExplorerScreen()
}
