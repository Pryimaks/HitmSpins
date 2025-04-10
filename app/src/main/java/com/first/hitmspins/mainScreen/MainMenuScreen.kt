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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.modifier.modifierLocalConsumer
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
    val songResId = R.raw.funkytown
    val context = LocalContext.current
    val isSoundEnabled by SettingsManager(context).
    soundEnabledFlow.collectAsState(initial = true)
    val settingsManager = remember { SettingsManager(context) }
    val isMusicEnabled by settingsManager.musicEnabledFlow.collectAsState(initial = true)
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(songResId, isMusicEnabled, isSoundEnabled) {
        when {
            !isMusicEnabled -> {
                MusicPlayer.setMusicEnabled(false)
                MusicPlayer.pause()
            }
            else -> {
                MusicPlayer.initialize(context, songResId)
                MusicPlayer.setSoundEnabled(isSoundEnabled)
                if (isSoundEnabled && !MusicPlayer.isPlaying()) {
                    MusicPlayer.play()
                }
            }
        }
    }


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
                Triple(
                    "Play",
                    painterResource(id = R.drawable.img_19),
                    { navController.navigate("musicSelection") }
                ),
                Triple(
                    "Challenges",
                    painterResource(id = R.drawable.img_20),
                    { navController.navigate("challengeMode") }
                ),
                Triple(
                    "Music Library",
                    painterResource(id = R.drawable.img_21),
                    { navController.navigate("musicLibraryMode") }
                )
            ).forEach { (text, imagePainter, action) ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = action,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(15.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                        ) {
                            Text(
                                text = text,
                                color = Color.Black,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(9.dp))
                            Image(
                                painter = imagePainter,
                                contentDescription = text,
                                modifier = Modifier.size(44.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .width(330.dp)
                                .height(3.dp)
                                .background(
                                    color = Color(0xFFFF4081),
                                    shape = RoundedCornerShape(100)
                                )
                                .align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(92.dp))

            Button(
                onClick = { navController.navigate("gameplay/{title}/{difficulty}") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDF1D73)),
                shape = RoundedCornerShape(30.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_22),
                    contentDescription = "start",
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        if (isPremiumOverlayVisible) {
            PremiumOverlay(onDismiss = { isPremiumOverlayVisible = false })
        }

    }
}

