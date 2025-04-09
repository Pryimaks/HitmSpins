package com.first.hitmspins.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.first.hitmspins.R
import com.first.hitmspins.music.MusicPlayer
import com.first.hitmspins.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun MainMenuScreen(navController: NavController) {
    var isPlaying by remember { mutableStateOf(true) }
    val songResId = R.raw.daddycool
    val context = LocalContext.current
    val isSoundEnabled by SettingsManager(context).
    soundEnabledFlow.collectAsState(initial = true)

    LaunchedEffect(songResId) {
        MusicPlayer.initializef(context, songResId)
        MusicPlayer.setSoundEnabledf(isSoundEnabled)
        if (isPlaying) MusicPlayer.play()
    }

    LaunchedEffect(isSoundEnabled) {
        MusicPlayer.setSoundEnabledf(isSoundEnabled)
        if (!isSoundEnabled) {
            MusicPlayer.pause()
        } else if (isPlaying) {
            MusicPlayer.play()
        }
    }

    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            progress = MusicPlayer.getCurrentPosition().toFloat() /
                    MusicPlayer.getDuration().coerceAtLeast(1)
            delay(100)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D0D1A), Color(0xFF1A1A2E))
                )
            )
    ) {
        var isPremiumOverlayVisible by remember { mutableStateOf(false) }
        Image(
            painter = painterResource(id = R.drawable.img_5),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            ProfileButton(navController)

            Spacer(modifier = Modifier.height(24.dp))

            GoPremiumButton(onClick = { isPremiumOverlayVisible = true })


            Spacer(modifier = Modifier.height(24.dp))

            listOf(
                "Play 🎮" to { navController.navigate("musicSelection") },
                "Challenges 🏆" to { navController.navigate("challengeMode") },
                "Music Library 🎵" to { navController.navigate("musicLibraryMode") }
            ).forEach { (text, action) ->
                Button(
                    onClick = action,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text, color = Color.Black, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            Spacer(modifier = Modifier.height(92.dp))

            Button(
                onClick = { navController.navigate("gameplay/{title}/{difficulty}") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text("Start Game", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (isPremiumOverlayVisible) {
            PremiumOverlay(onDismiss = { isPremiumOverlayVisible = false })
        }

    }
}

