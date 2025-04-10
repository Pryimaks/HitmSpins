package com.first.hitmspins.musicSelection.gameplay

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun TransparentNoteCircles(
    noteCount: Int = 4,
    onCircleSwipe: (index: Int, direction: SwipeDirection) -> Unit,
    onCircleTap: (Int) -> Unit,
    onCircleHoldStart: (Int) -> Unit,
    onCircleHoldEnd: (Int) -> Unit
) {
    val backgroundColor = Color(0xFF6B00C6).copy(alpha = 0.35f)
    val circleColor = Color(0xFFEE900188)
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f / 4f)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .align(Alignment.BottomCenter)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.White)
                .align(Alignment.TopCenter)
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(noteCount) { index ->
                var offsetX by remember { mutableStateOf(0f) }
                var isPressed by remember { mutableStateOf(false) }
                val interactionSource = remember { MutableInteractionSource() }

                val gestureModifier = Modifier
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                val direction = when {
                                    offsetX > 100f -> SwipeDirection.RIGHT
                                    offsetX < -100f -> SwipeDirection.LEFT
                                    else -> null
                                }
                                direction?.let { onCircleSwipe(index, it) }
                                offsetX = 0f
                            },
                            onHorizontalDrag = { _, dragAmount ->
                                offsetX += dragAmount
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                onCircleHoldStart(index)
                                tryAwaitRelease()
                                onCircleHoldEnd(index)
                                isPressed = false
                            }
                        )
                    }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1.1f)
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .then(gestureModifier)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onCircleTap(index)
                            }
                    ) {
                        if (isPressed) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        shape = CircleShape
                                        clip = true
                                        shadowElevation = 10f
                                    }
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                circleColor.copy(alpha = 0.2f),
                                                Color.Transparent
                                            ),
                                            radius = 400f
                                        ),
                                        shape = CircleShape
                                    )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 6.dp,
                                    color = circleColor,
                                    shape = CircleShape
                                )
                                .background(Color.Transparent)
                        )
                    }
                }
            }

        }
    }
}








