package com.first.hitmspins.musicSelection.gameplay

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun TransparentNoteCircles(noteCount: Int = 4, onCircleTap: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(noteCount) { index ->
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clickable { onCircleTap(index) },
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .graphicsLayer {
                            shadowElevation = 12f
                            shape = CircleShape
                            clip = true
                        }

                        .border(
                            BorderStroke(5.dp, Color(0xFFAA301FF)),
                            shape = CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(3.dp)
                        .background(Color.White, shape = RoundedCornerShape(50))
                        .offset(y = 10.dp)
                )
            }
        }
    }
}




