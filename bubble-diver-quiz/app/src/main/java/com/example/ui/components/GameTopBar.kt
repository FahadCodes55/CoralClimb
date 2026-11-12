package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@Composable
fun CalmTimerBar(
    progress: Float, // 1f down to 0f
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(200),
        label = "timer_bar"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0x55002244))
            .testTag("calm_timer_bar")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF80DEEA), // Soft cyan
                            Color(0xFF4DD0E1),
                            Color(0xFF26C6DA)
                        )
                    )
                )
        )
    }
}

@Composable
fun LifeBubbles(
    lives: Int, // 0 to 3
    maxLives: Int = 3,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.testTag("life_bubbles_row"),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxLives) {
            val isAlive = i <= lives
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (isAlive) Color.Transparent else Color(0x33FFFFFF))
                    .border(
                        width = 1.5.dp,
                        color = if (isAlive) Color(0xCCB3E5FC) else Color(0x44FFFFFF),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isAlive) {
                    Image(
                        painter = painterResource(id = R.drawable.bubble),
                        contentDescription = "Life Bubble $i",
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GameHeader(
    currentQuestionIndex: Int, // 1 to 8
    totalQuestions: Int,
    roundNumber: Int,
    lives: Int,
    timerProgress: Float,
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onExitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        // Top control row: Round badge, Lives, Mute, Exit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Exit button
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0x66002244))
                    .clickable(onClick = onExitClick)
                    .testTag("exit_game_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit to Menu",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Round & Progress Chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x88002244))
                    .border(1.dp, Color(0x66B2EBF2), RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp, vertical = 5.dp)
                    .testTag("round_progress_chip")
            ) {
                Text(
                    text = "Round $roundNumber • $currentQuestionIndex/$totalQuestions",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 3 Life bubbles
                LifeBubbles(lives = lives)

                // Mute toggle button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0x66002244))
                        .clickable(onClick = onToggleMute)
                        .testTag("mute_toggle_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                        contentDescription = if (isMuted) "Unmute" else "Mute",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Calm timer bar across the top
        CalmTimerBar(progress = timerProgress)
    }
}
