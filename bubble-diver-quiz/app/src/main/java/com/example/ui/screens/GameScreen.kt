package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnswerBubbleButton
import com.example.ui.components.DiverCharacter
import com.example.ui.components.GameHeader
import com.example.ui.viewmodel.GameUiState

@Composable
fun GameScreen(
    state: GameUiState,
    onAnswerClicked: (Int) -> Unit,
    onToggleMute: () -> Unit,
    onExitClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val question = state.currentQuestion ?: return

    val timerProgress = if (state.timerTotalMs > 0) {
        state.timerRemainingMs.toFloat() / state.timerTotalMs.toFloat()
    } else 0f

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .testTag("game_screen_root")
    ) {
        val screenHeight = maxHeight
        // Diver character is ~20% of the screen height
        val diverHeight = (screenHeight * 0.20f).coerceIn(160.dp, 195.dp)
        val bubbleSize = 88.dp // ~60% of original size

        // Background / Overall vertical stack
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Top Header with Timer, Lives, Round info, Mute, Exit
            GameHeader(
                currentQuestionIndex = state.currentStep + 1,
                totalQuestions = state.totalSteps,
                roundNumber = state.roundNumber,
                lives = state.lives,
                timerProgress = timerProgress,
                isMuted = state.isMuted,
                onToggleMute = onToggleMute,
                onExitClick = onExitClicked
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Question box above the answer bubbles
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xDD002244))
                    .border(2.dp, Color(0xFF80DEEA), RoundedCornerShape(22.dp))
                    .padding(horizontal = 18.dp, vertical = 14.dp)
                    .testTag("question_card"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = question.prompt,
                    color = Color.White,
                    fontSize = if (question.prompt.length > 40) 20.sp else 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. 4 Answer Bubbles in a neat, close 2x2 grid in the middle of the screen
            Column(
                modifier = Modifier
                    .testTag("answer_bubbles_grid"),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Top row: options 0 and 1
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (question.options.size > 0) {
                        AnswerBubbleButton(
                            text = question.options[0],
                            bubbleState = state.bubbleStates.getOrElse(0) { com.example.ui.components.BubbleState.DEFAULT },
                            onClick = { onAnswerClicked(0) },
                            size = bubbleSize,
                            tag = "bubble_option_0"
                        )
                    }
                    if (question.options.size > 1) {
                        AnswerBubbleButton(
                            text = question.options[1],
                            bubbleState = state.bubbleStates.getOrElse(1) { com.example.ui.components.BubbleState.DEFAULT },
                            onClick = { onAnswerClicked(1) },
                            size = bubbleSize,
                            tag = "bubble_option_1"
                        )
                    }
                }

                // Bottom row: options 2 and 3
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (question.options.size > 2) {
                        AnswerBubbleButton(
                            text = question.options[2],
                            bubbleState = state.bubbleStates.getOrElse(2) { com.example.ui.components.BubbleState.DEFAULT },
                            onClick = { onAnswerClicked(2) },
                            size = bubbleSize,
                            tag = "bubble_option_2"
                        )
                    }
                    if (question.options.size > 3) {
                        AnswerBubbleButton(
                            text = question.options[3],
                            bubbleState = state.bubbleStates.getOrElse(3) { com.example.ui.components.BubbleState.DEFAULT },
                            onClick = { onAnswerClicked(3) },
                            size = bubbleSize,
                            tag = "bubble_option_3"
                        )
                    }
                }
            }
        }

        // 4. Diver Character placed at the bottom, swimming visibly upward each correct step
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            DiverCharacter(
                step = state.currentStep,
                totalSteps = state.totalSteps,
                animationState = state.diverAnimation,
                characterHeight = diverHeight,
                maxSwimAscentDp = 150f // visibly swims upward toward the middle!
            )
        }

        // Floating feedback message banner (e.g. "Try again!", "Great job!")
        AnimatedVisibility(
            visible = state.feedbackMessage != null,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            state.feedbackMessage?.let { msg ->
                val isSuccess = msg != "Try again!" && msg != "Time's up!"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (isSuccess) Color(0xFF2E7D32) else Color(0xFFD32F2F))
                        .border(2.dp, Color.White, RoundedCornerShape(24.dp))
                        .padding(horizontal = 28.dp, vertical = 14.dp)
                        .testTag("feedback_banner"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = msg,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}
