package com.first.hitmspins.musicSelection.gameplay

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
import com.first.hitmspins.R
import coil.compose.AsyncImage
import com.first.hitmspins.music.MusicPlayer
import kotlinx.coroutines.delay

@Composable
fun GameplayScreen() {
    var isPlaying by remember { mutableStateOf(true) }
    var score by remember { mutableStateOf(0) }
    var combo by remember { mutableStateOf(10) }
    var progress by remember { mutableStateOf(0f) }

    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.stronger) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            mediaPlayer.start()
            while (mediaPlayer.isPlaying) {
                progress = mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration
                delay(100)
            }
        } else {
            mediaPlayer.pause()
        }
    }

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

            IconButton(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .background(Color.Magenta, shape = CircleShape)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
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
            TrackArea(isPlaying) { noteType ->
                when (noteType) {
                    "tap" -> score += 100
                    "hold" -> score += 150
                    "swipe" -> score += 120
                }
                combo += 1
            }
        }
    }
}
