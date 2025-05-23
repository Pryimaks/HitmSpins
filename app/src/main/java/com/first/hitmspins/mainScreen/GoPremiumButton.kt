package com.first.hitmspins.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.first.hitmspins.R

@Composable
fun GoPremiumButton(onClick: () -> Unit) {
    var isPremiumOverlayVisible by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A0DAD)),
        shape = RoundedCornerShape(25.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.offset(x = (-10).dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_18),
                    contentDescription = "Premium badge",
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Fit
                )
             Spacer(modifier = Modifier.width(9.dp))
                Image(
                    painter = painterResource(id = R.drawable.img_17),
                    contentDescription = "Diamond icon",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(y = (-4).dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }


}

@Composable
fun PremiumOverlay(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(0x80000000))
          .zIndex(3f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(Color(0xFFDAAFFF), shape = RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable { onDismiss() }
                        .size(34.dp)
                        .background(Color(0xFFAAAAAA), shape = RoundedCornerShape(88.dp)),
                    tint = Color.Gray
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.img_12),
                        contentDescription = "Premium Icon",
                        modifier = Modifier
                            .size(94.dp)
                            .padding(bottom = 16.dp)
                    )

                    PremiumFeatureButton("Unlocking Exclusive Tracks")
                    PremiumFeatureButton("Access to special modes and challenges")

                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color(0xFFF4B400))
                            .padding(horizontal = 20.dp, vertical = 15.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)

                    ) {
                        Text(
                            text = "$3.99 One-Time Purchase",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDF1D73)
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text("Upgrade to Premium",
                            color = Color.White,
                            fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumFeatureButton(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFF6B00C6))
            .padding(vertical = 17.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

