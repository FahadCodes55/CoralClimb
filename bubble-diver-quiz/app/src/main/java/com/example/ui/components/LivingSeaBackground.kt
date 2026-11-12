package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import kotlin.math.sin

data class FishSpec(
    val drawableRes: Int,
    val relativeY: Float, // relative screen height
    val widthDp: Dp,
    val heightDp: Dp,
    val durationMs: Int,
    val movingLeft: Boolean,
    val wobbleSpeed: Float,
    val wobbleAmp: Float
)

@Composable
fun LivingSeaBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .testTag("living_sea_background")
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // 1. Static underwater background image, scaled sharp with ContentScale.Crop and FilterQuality.High
        // Zero blur, zero transparency overlay, and zero dark gradient over it
        val bgBitmap = ImageBitmap.imageResource(id = R.drawable.background)
        Image(
            bitmap = bgBitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.High
        )

        val infiniteTransition = rememberInfiniteTransition(label = "sea_anim")

        // 3. Gentle rising bubbles behind gameplay
        val bubbleProgress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(9000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "bubbles_rise"
        )

        val bubbleOffsets = remember {
            listOf(
                Triple(0.12f, 0.4f, 10f),
                Triple(0.28f, 0.7f, 14f),
                Triple(0.48f, 0.2f, 8f),
                Triple(0.68f, 0.8f, 16f),
                Triple(0.85f, 0.5f, 12f),
                Triple(0.35f, 0.9f, 9f),
                Triple(0.78f, 0.3f, 15f)
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            for ((relX, phaseShift, radius) in bubbleOffsets) {
                val currentP = (bubbleProgress + phaseShift) % 1f
                val y = h * (1f - currentP)
                val wobbleX = sin(currentP * Math.PI * 4 + phaseShift * 10).toFloat() * 14f
                val x = (relX * w) + wobbleX
                val alpha = (sin(currentP * Math.PI).toFloat() * 0.45f).coerceIn(0f, 0.5f)

                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = radius,
                    center = Offset(x, y)
                )
                // highlight spark in bubble
                drawCircle(
                    color = Color.White.copy(alpha = (alpha * 1.6f).coerceAtMost(0.8f)),
                    radius = radius * 0.35f,
                    center = Offset(x - radius * 0.3f, y - radius * 0.3f)
                )
            }
        }

        // 4. Moving fish sprites (fish1 to fish4): about 2 times larger than before
        // Behind bubbles and text
        val fishList = remember {
            listOf(
                // fish1: seahorse, gentle drift right, ~2x larger
                FishSpec(
                    drawableRes = R.drawable.fish1,
                    relativeY = 0.22f,
                    widthDp = 220.dp,
                    heightDp = 125.dp,
                    durationMs = 21000,
                    movingLeft = false,
                    wobbleSpeed = 2.8f,
                    wobbleAmp = 8f
                ),
                // fish2: angelfish, swimming left (flipped horizontally), ~2x larger
                FishSpec(
                    drawableRes = R.drawable.fish2,
                    relativeY = 0.38f,
                    widthDp = 200.dp,
                    heightDp = 115.dp,
                    durationMs = 17000,
                    movingLeft = true,
                    wobbleSpeed = 2.2f,
                    wobbleAmp = 10f
                ),
                // fish3: turtle, slow swimming right, ~2x larger
                FishSpec(
                    drawableRes = R.drawable.fish3,
                    relativeY = 0.60f,
                    widthDp = 150.dp,
                    heightDp = 260.dp,
                    durationMs = 25000,
                    movingLeft = false,
                    wobbleSpeed = 1.6f,
                    wobbleAmp = 6f
                ),
                // fish4: pink round fish, swimming left (flipped horizontally), ~2x larger
                FishSpec(
                    drawableRes = R.drawable.fish4,
                    relativeY = 0.78f,
                    widthDp = 200.dp,
                    heightDp = 115.dp,
                    durationMs = 18000,
                    movingLeft = true,
                    wobbleSpeed = 3.0f,
                    wobbleAmp = 9f
                )
            )
        }

        fishList.forEachIndexed { index, fish ->
            val progress by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(fish.durationMs, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "fish_swim_$index"
            )

            val offscreenMargin = 220.dp
            val startX = if (fish.movingLeft) screenWidth + offscreenMargin else -offscreenMargin
            val endX = if (fish.movingLeft) -offscreenMargin else screenWidth + offscreenMargin
            val currentX = startX + (endX - startX) * progress

            val wobbleY = (sin((progress * Math.PI * 2 * fish.wobbleSpeed).toDouble()) * fish.wobbleAmp).dp
            val currentY = screenHeight * fish.relativeY + wobbleY

            Image(
                painter = painterResource(id = fish.drawableRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .offset(x = currentX, y = currentY)
                    .size(width = fish.widthDp, height = fish.heightDp)
                    .graphicsLayer {
                        // Flip horizontally when swimming left (since original images face right)
                        scaleX = if (fish.movingLeft) -1f else 1f
                        alpha = 0.92f
                    }
            )
        }

        // 5. Forefront gameplay content (answer bubbles, text, character) rendered cleanly on top of the fish
        content()
    }
}
