package com.first.hitmspins.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LeaderboardScreen(navController: NavController) {
    val leaderboard = listOf(
        Triple(1, "Player 1", "10,000 XP"),
        Triple(2, "Player 2", "8,500 XP"),
        Triple(3, "Player 3", "7,200 XP")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C1E))
            .padding(16.dp)
    ) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFF4081)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "🏆 Leaderboard",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        leaderboard.forEach { (rank, name, xp) ->
            LeaderboardCard(rank = rank, name = name, xp = xp)
        }
    }
}

@Composable
fun LeaderboardCard(rank: Int, name: String, xp: String) {
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color.White
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .border(2.dp, Color(0xFFFF4081), RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "$rank.",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = rankColor
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "XP",
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = xp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

