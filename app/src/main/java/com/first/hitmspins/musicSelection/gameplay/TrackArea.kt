package com.first.hitmspins.musicSelection.gameplay

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import okhttp3.internal.platform.android.AndroidLogHandler.close
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.io.path.moveTo

@Composable
fun TrackLinesBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val trackCount = 4

        val topTrackWidth = width / 9
        val bottomTrackWidth = width / trackCount

        for (i in 0 until trackCount) {
            val topCenter = width / 3 - topTrackWidth * 2 + topTrackWidth * i * 2 + topTrackWidth / 2
            val bottomLeft = i * bottomTrackWidth
            val bottomRight = bottomLeft + bottomTrackWidth

            val path = Path().apply {
                moveTo(topCenter - topTrackWidth / 2, 0f)
                lineTo(topCenter + topTrackWidth / 2, 0f)
                lineTo(bottomRight, height)
                lineTo(bottomLeft, height)
                close()
            }

            drawPath(
                path = path,
                color = Color(0xAA7C4DFF),
                style = Fill
            )
        }
    }
}



@Composable
fun TrackArea(isPlaying: Boolean, onNoteHit: (String) -> Unit) {
    val noteTypes = listOf("tap", "hold", "swipe", "tap")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {

        TrackLinesBackground(
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            noteTypes.forEach { type ->
                TrackLane(isPlaying, type) {
                    onNoteHit(type)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(noteTypes.size) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color(0xFFAA00FF), shape = CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .shadow(12.dp, CircleShape)
                )
            }
        }
    }
}










