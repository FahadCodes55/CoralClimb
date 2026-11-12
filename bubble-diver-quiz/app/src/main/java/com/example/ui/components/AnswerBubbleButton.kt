package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlin.math.sin

enum class BubbleState {
    DEFAULT,
    CORRECT_POPPING,
    WRONG_WOBBLE,
    DISABLED
}

@Composable
fun AnswerBubbleButton(
    text: String,
    bubbleState: BubbleState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 88.dp, // ~60% of original 142dp
    tag: String = "answer_bubble"
) {
    val popScale = remember { Animatable(1f) }
    val popAlpha = remember { Animatable(1f) }
    val shakeAnim = remember { Animatable(0f) }

    LaunchedEffect(bubbleState) {
        when (bubbleState) {
            BubbleState.CORRECT_POPPING -> {
                popScale.animateTo(1.35f, tween(260, easing = FastOutSlowInEasing))
                popAlpha.animateTo(0f, tween(160, easing = LinearEasing))
            }
            BubbleState.WRONG_WOBBLE -> {
                shakeAnim.snapTo(0f)
                shakeAnim.animateTo(1f, tween(400, easing = LinearEasing))
                shakeAnim.snapTo(0f)
            }
            BubbleState.DEFAULT, BubbleState.DISABLED -> {
                popScale.snapTo(1f)
                popAlpha.snapTo(1f)
                shakeAnim.snapTo(0f)
            }
        }
    }

    val shakeX = sin(shakeAnim.value * Math.PI * 4).toFloat() * 10f

    Box(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                scaleX = popScale.value
                scaleY = popScale.value
                alpha = popAlpha.value
                translationX = shakeX
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = bubbleState == BubbleState.DEFAULT,
                onClick = onClick
            )
            .testTag(tag),
        contentAlignment = Alignment.Center
    ) {
        // 1. Bubble Image as the background shape
        Image(
            painter = painterResource(id = R.drawable.bubble),
            contentDescription = "Answer Bubble",
            modifier = Modifier.size(size)
        )

        // 2. Large, highly readable centered answer text
        val fontSize = when {
            text.length <= 4 -> 20.sp
            text.length <= 8 -> 16.sp
            text.length <= 13 -> 13.5.sp
            else -> 12.sp
        }

        Text(
            text = text,
            color = Color(0xFF002855),
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = if (fontSize > 15.sp) 22.sp else 16.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
