package com.example.sample_front_screen
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedConnectedBoxes() {
    val boxSize = 50.dp
    val density = LocalDensity.current
    val boxSizePx = with(density) { boxSize.toPx() }

    val infiniteTransition = rememberInfiniteTransition()

    // Box 1 animation
    val box1Angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Box 2 animation
    val box2Angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val centerX = constraints.maxWidth / 2f
        val centerY = constraints.maxHeight / 2f

        // Calculate box positions in pixels
        val box1Position = remember(box1Angle) {
            Offset(
                x = centerX + 100f * cos(box1Angle) - boxSizePx / 2,
                y = centerY + 100f * sin(box1Angle) - boxSizePx / 2
            )
        }

        val box2Position = remember(box2Angle) {
            Offset(
                x = centerX + 200f * cos(box2Angle * 2) - boxSizePx / 2,
                y = centerY + 170f * sin(box2Angle) - boxSizePx / 2
            )
        }

        // Draw connecting line first
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.Black,
                start = Offset(
                    box1Position.x + boxSizePx / 2,
                    box1Position.y + boxSizePx / 2
                ),
                end = Offset(
                    box2Position.x + boxSizePx / 2,
                    box2Position.y + boxSizePx / 2
                ),
                strokeWidth = 4f
            )
        }

        // Draw boxes
        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { box1Position.x.toDp() },
                    y = with(density) { box1Position.y.toDp() }
                )
                .size(boxSize)
                .background(Color.Blue)
        )

        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { box2Position.x.toDp() },
                    y = with(density) { box2Position.y.toDp() }
                )
                .size(boxSize)
                .background(Color.Red)
        )
    }
}