package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.RoundStats

@Composable
fun RoundCompleteScreen(
    stats: RoundStats,
    totalStars: Int,
    onNextRound: () -> Unit,
    onHomeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Star bounce animations
    val star1Scale = remember { Animatable(0f) }
    val star2Scale = remember { Animatable(0f) }
    val star3Scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        if (stats.starsEarned >= 1) {
            star1Scale.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
        }
        if (stats.starsEarned >= 2) {
            star2Scale.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
        }
        if (stats.starsEarned >= 3) {
            star3Scale.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("round_complete_screen")
    ) {
        // 1. safe_place.png full screen sharp with FilterQuality.High
        val safePlaceBitmap = ImageBitmap.imageResource(id = R.drawable.safe_place)
        Image(
            bitmap = safePlaceBitmap,
            contentDescription = "Safe Place - Round Complete",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.High
        )

        // 2. Translucent celebratory card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Celebration dialog
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xD9002244))
                    .border(2.5.dp, Color(0xFFFFD54F), RoundedCornerShape(28.dp))
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .testTag("round_stats_card"),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Round ${stats.roundNumber} Complete!",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "You reached the Safe Reef!",
                        color = Color(0xFF80DEEA),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // 1 to 3 stars based on mistakes
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Star 1
                        val active1 = stats.starsEarned >= 1
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star 1",
                            tint = if (active1) Color(0xFFFFCA28) else Color(0x55FFFFFF),
                            modifier = Modifier
                                .size(50.dp)
                                .graphicsLayer {
                                    scaleX = if (active1) star1Scale.value else 0.8f
                                    scaleY = if (active1) star1Scale.value else 0.8f
                                }
                        )

                        // Star 2 (middle slightly larger)
                        val active2 = stats.starsEarned >= 2
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star 2",
                            tint = if (active2) Color(0xFFFFCA28) else Color(0x55FFFFFF),
                            modifier = Modifier
                                .size(64.dp)
                                .graphicsLayer {
                                    scaleX = if (active2) star2Scale.value else 0.8f
                                    scaleY = if (active2) star2Scale.value else 0.8f
                                }
                        )

                        // Star 3
                        val active3 = stats.starsEarned >= 3
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star 3",
                            tint = if (active3) Color(0xFFFFCA28) else Color(0x55FFFFFF),
                            modifier = Modifier
                                .size(50.dp)
                                .graphicsLayer {
                                    scaleX = if (active3) star3Scale.value else 0.8f
                                    scaleY = if (active3) star3Scale.value else 0.8f
                                }
                        )
                    }

                    Text(
                        text = when (stats.starsEarned) {
                            3 -> "Perfect swim! 0 mistakes"
                            2 -> "Great diving! ${stats.mistakes} mistake"
                            else -> "Nice effort! Safe and sound"
                        },
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0x660288D1))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Total Stars: $totalStars",
                            color = Color(0xFFFFE082),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Action Buttons: Next Round & Home
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onNextRound,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(8.dp, RoundedCornerShape(30.dp))
                        .testTag("next_round_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB300)
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Next Round",
                            color = Color(0xFF003366),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                        Icon(
                            imageVector = Icons.Default.NavigateNext,
                            contentDescription = null,
                            tint = Color(0xFF003366),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                OutlinedButton(
                    onClick = onHomeClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("round_complete_home_button"),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0x66002244)
                    ),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = "Home Menu",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
