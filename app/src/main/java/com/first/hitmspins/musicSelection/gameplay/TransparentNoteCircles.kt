package com.first.hitmspins.musicSelection.gameplay

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun TransparentNoteCircles(noteCount: Int = 4, onCircleTap: (Int) -> Unit) {
    val backgroundColor = Color(0xFF6B00C6).copy(alpha = 0.4f)
    val circleColor = Color(0xFFEE900188)

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

                val interactionSource = remember { MutableInteractionSource() }

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
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = LocalIndication.current
                            ) {
                                onCircleTap(index)
                            }
                    ) {
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
                                            circleColor.copy(alpha = 0f),
                                            Color.Transparent
                                        ),
                                        radius = 400f
                                    ),
                                    shape = CircleShape
                                )
                        )

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








