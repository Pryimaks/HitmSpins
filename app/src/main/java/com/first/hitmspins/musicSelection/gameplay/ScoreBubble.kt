package com.first.hitmspins.musicSelection.gameplay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ScoreBubble(text: String, modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(60.dp)
            .background(Color(0xFFEE0979), shape = CircleShape)
            .border(2.dp, Color.White, CircleShape)
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
