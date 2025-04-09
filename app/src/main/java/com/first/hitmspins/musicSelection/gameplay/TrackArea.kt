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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import okhttp3.internal.platform.android.AndroidLogHandler.close
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.io.path.moveTo

@Composable
fun TrackLinesBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.background(Color(0xFFBB509961119F))) {
        val width = size.width
        val height = size.height
        val trackCount = 4

        val topWidth = width / 6
        val bottomWidth = width / trackCount

        for (i in 0 until trackCount) {
            val topLeft = width / 2 - (topWidth * trackCount / 2) + i * topWidth
            val topRight = topLeft + topWidth
            val bottomLeft = i * bottomWidth
            val bottomRight = bottomLeft + bottomWidth

            val path = Path().apply {
                moveTo(topLeft, 0f)
                lineTo(topRight, 0f)
                lineTo(bottomRight, height)
                lineTo(bottomLeft, height)
                close()
            }

            drawPath(path, Brush.verticalGradient(
                colors = listOf(Color(0x66000000), Color(0xFFAA66FF)),
                startY = 0f,
                endY = height
            ))

            drawLine(Color.White, Offset(topLeft, 0f), Offset(bottomLeft, height), strokeWidth = 11f)
            drawLine(Color.White, Offset(topRight, 0f), Offset(bottomRight, height), strokeWidth = 11f)
        }
    }
}


@Composable
fun TrackArea(
    isPlaying: Boolean,
    difficulty: String,
    onNoteHit: (String) -> Unit
) {
    val laneCount = 4
    val lanesNotes = remember { List(laneCount) { mutableStateListOf<Note>() } }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        TrackLinesBackground(modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(laneCount) { index ->
                TrackLane(isPlaying, index, lanesNotes[index], difficulty)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            TransparentNoteCircles(noteCount = laneCount) { tappedIndex ->
                val notes = lanesNotes[tappedIndex]
                val hitNote = notes.find { it.offset.value in 400f..470f }
                if (hitNote != null) {
                    onNoteHit(hitNote.type)

                    notes.remove(hitNote)
                }
            }
        }
    }
}











