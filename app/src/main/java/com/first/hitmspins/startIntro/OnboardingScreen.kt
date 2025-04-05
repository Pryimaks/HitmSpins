package com.first.hitmspins.startIntro

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.first.hitmspins.R
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()

    val pages = listOf(
        Pair(R.drawable.img_1, "Tap, Hold & Swipe to the Rhythm!"),
        Pair(R.drawable.img_2, "Play various music genres!"),
        Pair(R.drawable.img_3, "Challenge yourself in fast-paced rhythm battles!"),
        Pair(R.drawable.img_4, "Unlock exclusive songs and game modes!")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D1A))
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0D1A))
        ) {
            TextButton(
                onClick = onFinish,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .zIndex(1f)
            )
            {
                Text(
                    text = "Skip",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                        Image(
                            painter = painterResource(id = pages[page].first),
                            contentDescription = "Onboarding Image",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = pages[page].second,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(horizontalArrangement = Arrangement.Center) {
                            repeat(pages.size) { index ->
                                val color =
                                    if (pagerState.currentPage == index) Color.Cyan else Color.White
                                Box(
                                    modifier = Modifier
                                        .padding(9.dp)
                                        .size(13.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 150.dp)
            ) {
                val buttonModifier = Modifier
                    .width(200.dp)
                    .height(50.dp)

                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF4081),
                    contentColor = Color.White
                )

                if (pagerState.currentPage < pages.size - 1) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = buttonModifier,
                        colors = buttonColors
                    ) {
                        Text("Next")
                    }
                } else {
                    Button(
                        onClick = onFinish,
                        modifier = buttonModifier,
                        colors = buttonColors
                    ) {
                        Text("Start Playing")
                    }
                }
            }
        }
    }
