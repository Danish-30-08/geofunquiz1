package com.example.geofunquiz.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Precise Colors from Image
val BackgroundColor = Color(0xFFFFFBE6)
val OrangeGradient = listOf(Color(0xFFFF8C42), Color(0xFFF05D5E))
val FlagCardBg = Color(0xFFE3F2FD)
val FlagIconBg = Color(0xFF3B82F6)
val CapitalIconBg = Color(0xFFDCFCE7)
val TriviaIconBg = Color(0xFFFFEDD5)

@Composable
fun JuniorExplorerScreen(
    xp: Int = 0,
    onStartQuiz: () -> Unit = {},
    onStartCapitalsQuiz: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            HeaderSection(xp = xp)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            BonusLevelCard(onStartQuiz = onStartCapitalsQuiz)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Choose Your Mission",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Flag Detective Card
            MissionCard(
                icon = Icons.Rounded.Public,
                iconColor = Color.White,
                iconBg = FlagIconBg,
                cardBg = FlagCardBg,
                title = "Flag Detective",
                subtitle = "Guess the country flag",
                playButtonColor = Color(0xFF3B82F6),
                onPlayClick = onStartQuiz
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Capital City Finder Card
            MissionCard(
                icon = Icons.Rounded.LocationOn,
                iconColor = Color(0xFF22C55E),
                iconBg = CapitalIconBg,
                cardBg = Color.White,
                title = "Capital City Finder",
                subtitle = "Match capitals to countries",
                playButtonColor = Color(0xFF22C55E),
                onPlayClick = onStartCapitalsQuiz,
                showBottomGradient = true,
                gradientColor = Color(0xFFDCFCE7)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Fun Facts Trivia Card
            MissionCard(
                icon = Icons.Rounded.Star,
                iconColor = Color(0xFFF97316),
                iconBg = TriviaIconBg,
                cardBg = Color.White,
                title = "Fun Facts Trivia",
                subtitle = "Trivia about culture & history",
                playButtonColor = Color(0xFFF97316),
                showBottomGradient = true,
                gradientColor = Color(0xFFFFEDD5)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HeaderSection(xp: Int = 0) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(text = "Hi there,", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(
                text = "Junior Explorer!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "🤩", fontSize = 32.sp)
        }

        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$xp", fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color(0xFF1E293B))
                    Text(text = "XP", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        }
    }
}

@Composable
fun BonusLevelCard(onStartQuiz: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .shadow(12.dp, RoundedCornerShape(32.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(OrangeGradient))
                .padding(24.dp)
        ) {
            // Background Trophy Icon
            Icon(
                imageVector = Icons.Rounded.EmojiEvents,
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 30.dp),
                tint = Color.White.copy(alpha = 0.15f)
            )

            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                Surface(
                    color = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "BONUS LEVEL",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                
                Column {
                    Text(text = "Capital Cities Blitz", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    Text(text = "Earn 2x XP before the clock runs out!", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                }

                Button(
                    onClick = onStartQuiz,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(44.dp).shadow(4.dp, RoundedCornerShape(12.dp)),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    Text(text = "Start Quest", color = Color(0xFFF05D5E), fontWeight = FontWeight.Black, fontSize = 15.sp)
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
    cardBg: Color,
    title: String,
    subtitle: String,
    playButtonColor: Color,
    onPlayClick: () -> Unit = {},
    showBottomGradient: Boolean = false,
    gradientColor: Color = Color.Transparent
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box {
            if (showBottomGradient) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .align(Alignment.BottomCenter)
                        .background(gradientColor)
                )
            }
            
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, fontSize = 19.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E293B))
                    Text(text = subtitle, fontSize = 13.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                }
                
                IconButton(
                    onClick = onPlayClick,
                    modifier = Modifier
                        .size(42.dp)
                        .background(playButtonColor, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.shadow(16.dp)
    ) {
        val items = listOf(
            Triple("home", Icons.Rounded.Home, "Home"),
            Triple("explore", Icons.Rounded.Search, "Explore"),
            Triple("rank", Icons.AutoMirrored.Rounded.TrendingUp, "Rank"),
            Triple("profile", Icons.Rounded.PersonOutline, "Profile")
        )

        items.forEach { (route, icon, label) ->
            NavigationBarItem(
                selected = selectedTab == route,
                onClick = { onTabSelected(route) },
                icon = { Icon(icon, null) },
                label = { Text(label, fontWeight = FontWeight.Bold, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF3B82F6),
                    selectedTextColor = Color(0xFF3B82F6),
                    indicatorColor = Color(0xFFE3F2FD),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JuniorExplorerScreen(xp = 1200)
}
