package com.first.hitmspins.musicSelection.gameplay

import android.R.attr.enabled
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.first.hitmspins.R
import coil.compose.AsyncImage
import com.first.hitmspins.MainActivity
import com.first.hitmspins.mainScreen.SettingsManager
import com.first.hitmspins.music.MusicPlayer
import com.first.hitmspins.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

@Composable
fun GameplayScreen(
    navController: NavController,
    title: String,
    difficulty: String,
    viewModel: GameViewModel = viewModel()
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }

    val isSoundEnabled by SettingsManager(context).
    soundEnabledFlow.collectAsState(initial = true)

    var score by remember { mutableStateOf(0) }
    var combo by remember { mutableStateOf(10) }
    var progress by remember { mutableStateOf(0f) }

    val backStackEntry = navController.currentBackStackEntry
    val title = backStackEntry?.arguments?.getString("title") ?: "Default"
    val difficulty = backStackEntry?.arguments?.getString("difficulty") ?: "Easy"


    val songResId = when (title) {
        "Electro Rush" -> R.raw.stronger
        "Rock Inferno" -> R.raw.acdc
        "Jazz Funk Vibe" -> R.raw.every
        "Neo Swing" -> R.raw.neo
        else -> R.raw.bye
    }

    var showPauseOverlay by remember { mutableStateOf(false) }


    val mediaPlayer = remember { MediaPlayer.create(context, songResId) }

    LaunchedEffect(songResId) {
        MusicPlayer.initialize(context, songResId)
        MusicPlayer.setSoundEnabled(isSoundEnabled)
        if (isPlaying) MusicPlayer.play()
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            MusicPlayer.play()
        } else {
            MusicPlayer.pause()
        }
    }

    LaunchedEffect(isSoundEnabled) {
        MusicPlayer.setSoundEnabled(isSoundEnabled)
        if (!isSoundEnabled) {
            MusicPlayer.pause()
        } else if (isPlaying) {
            MusicPlayer.play()
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            progress = MusicPlayer.getCurrentPosition().toFloat() /
                    MusicPlayer.getDuration().coerceAtLeast(1)
            delay(100)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            if (MusicPlayer.isPlaying() && MusicPlayer.getCurrentPosition() >=
                MusicPlayer.getDuration() - 100) {

                viewModel.saveScore(score, combo)
                navController.navigate("gameOverScreen/${title}/Easy/${score}/${combo}") {
                    popUpTo("gameplay/${title}/${difficulty}") { inclusive = true }
                }
                break
            }
            delay(100)
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            MusicPlayer.pause()
            viewModel.saveScore(score, combo)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_5),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (!showPauseOverlay) {
                    IconButton(
                        onClick = {
                            isPlaying = !isPlaying
                            showPauseOverlay = !isPlaying
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                            .background(Color.Magenta, shape = CircleShape)
                    ) {
                        if (isPlaying) {
                            Image(
                                painter = painterResource(id = R.drawable.pause),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }

                Text(
                    text = "🔥 Combo x$combo",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
                )
                Text(
                    text = "🎵 Score: $score",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                )
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .padding(horizontal = 16.dp),
                color = Color(0xFFFF4081)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .background(Color.Black),
                contentAlignment = Alignment.BottomCenter
            ) {
                TrackArea(isPlaying, difficulty) { noteType ->
                    when (noteType) {
                        "tap" -> score += 100
                        "hold" -> score += 150
                        "swipe" -> score += 120
                    }
                    combo += 1
                }
            }
        }

        if (showPauseOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000))
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pause",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    IconButton(
                        onClick = {
                            isPlaying = true
                            showPauseOverlay = false
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Resume",
                            tint = Color.Black,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }

}


