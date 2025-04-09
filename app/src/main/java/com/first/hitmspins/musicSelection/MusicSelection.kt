package com.first.hitmspins.musicSelection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.first.hitmspins.R
import com.first.hitmspins.music.MusicPlayer

@Composable
fun MusicSelectionScreen(onBack: () -> Unit,
                         onTrackSelected: (String, String) -> Unit,
                         navController: NavController) {
    MusicPlayer.setSoundEnabled(true)
    val tracks = listOf(
        Triple("Electro Rush", R.drawable.img_8, "Easy"),
        Triple("Rock Inferno", R.drawable.img_9, "Medium"),
        Triple("Jazz Funk Vibe", R.drawable.img_10, "Hard"),
        Triple("Neo Swing", R.drawable.img_11, "Insane")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF1A1A2E))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFF4081), shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Music Selection",
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(3f)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        tracks.forEach { (title, image, difficulty) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(2.dp, Color(0xFFFF4081), RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = title,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(title, color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star",
                                    tint = Color.Yellow
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        val difficultyColor = when (difficulty) {
                            "Easy" -> Color(0xFF4CAF50)
                            "Medium" -> Color(0xFF2196F3)
                            "Hard" -> Color(0xFFF44336)
                            "Insane" -> Color(0xFFE040FB)
                            else -> Color.Gray
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("gameplay/$title/$difficulty")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Play Now", color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(containerColor = difficultyColor),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(difficulty, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}





