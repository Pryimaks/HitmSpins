package com.first.hitmspins

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen() {
    var pageIndex by remember { mutableStateOf(0) }
    val pages = listOf(
        "Tap, Hold & Swipe to the Rhythm!",
        "Play various music genres!",
        "Challenge yourself in fast-paced rhythm battles!",
        "Unlock exclusive songs and game modes!"
    )

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0D0D1A)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "HitmSpins Logo",
            modifier = Modifier.size(100.dp)
        )
        Text(text = "Feel the Beat. Hit the Notes.", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = pages[pageIndex], color = Color.Cyan, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Button(onClick = { if (pageIndex < pages.size - 1) pageIndex++ }) {
                Text("Next")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {  }) {
                Text("Start Playing")
            }
        }
    }
}