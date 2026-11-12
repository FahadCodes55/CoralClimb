package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.DiverAnimationState
import kotlin.math.sin

@Composable
fun DiverCharacter(
    step: Int, // 0 to 8 correct answers
    totalSteps: Int = 8,
    animationState: DiverAnimationState,
    modifier: Modifier = Modifier,
    characterHeight: Dp = 190.dp, // roughly 20% of screen height
    maxSwimAscentDp: Float = 160f // total upward travel distance across 8 steps
) {
    // Character aspect ratio: 677 x 369 = 1.8347:1
    val characterWidth = characterHeight * (677f / 369f)

    // Continuous idle floating bob and gentle sway
    val infiniteTransition = rememberInfiniteTransition(label = "diver_idle")
    val idlePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "idle_phase"
    )

    // Smooth visible vertical swim ascent: step 0 to 8
    val targetClimbRatio = (step.toFloat() / totalSteps.toFloat()).coerceIn(0f, 1f)
    val animatedClimb by animateFloatAsState(
        targetValue = targetClimbRatio,
        animationSpec = tween(650, easing = FastOutSlowInEasing),
        label = "diver_climb"
    )

    // Interactive action animation (bounce, wobble, fall)
    val bounceAnim = remember { Animatable(0f) }
    val wobbleAnim = remember { Animatable(0f) }
    val fallAnim = remember { Animatable(0f) }

    LaunchedEffect(animationState) {
        when (animationState) {
            DiverAnimationState.HAPPY_BOUNCE -> {
                bounceAnim.snapTo(0f)
                bounceAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
                bounceAnim.snapTo(0f)
            }
            DiverAnimationState.WRONG_WOBBLE -> {
                wobbleAnim.snapTo(0f)
                wobbleAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(600, easing = LinearEasing)
                )
                wobbleAnim.snapTo(0f)
            }
            DiverAnimationState.FALL_DOWN -> {
                fallAnim.snapTo(1f)
                fallAnim.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(1200, easing = FastOutSlowInEasing)
                )
            }
            else -> {}
        }
    }

    val idleBob = sin(idlePhase * Math.PI * 2).toFloat() * 7f
    val idleTilt = sin(idlePhase * Math.PI * 2).toFloat() * 3.5f

    val happyOffset = -sin(bounceAnim.value * Math.PI).toFloat() * 26f
    val happyScale = 1f + sin(bounceAnim.value * Math.PI).toFloat() * 0.12f

    val wobbleAngle = sin(wobbleAnim.value * Math.PI * 5).toFloat() * 11f
    val wobbleScale = 1f - sin(wobbleAnim.value * Math.PI).toFloat() * 0.06f

    // Upward swim distance: Each step climbs visibly upward
    val climbOffsetPx = -(animatedClimb * maxSwimAscentDp)

    Box(
        modifier = modifier
            .testTag("diver_character_box"),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.character),
            contentDescription = "Diver Character",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(characterWidth)
                .height(characterHeight)
                .graphicsLayer {
                    translationY = climbOffsetPx + idleBob + happyOffset
                    rotationZ = idleTilt + wobbleAngle
                    scaleX = happyScale * wobbleScale
                    scaleY = happyScale * wobbleScale
                }
                .testTag("diver_character_image")
        )
    }
}
