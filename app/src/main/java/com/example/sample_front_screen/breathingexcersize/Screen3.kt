package com.example.sample_front_screen.breathingexcersize

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.fontfunctions.SubHeadingText
import com.example.sample_front_screen.reusablefunctions.BotwithUpperChat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// Define breathing phases with durations
enum class BreathingPhase(val displayText: String, val duration: Int) {
    INHALE("Breathe In", 3000),
    HOLD("Hold", 5000),
    EXHALE("Exhale", 3000),
    PAUSE("Get Ready", 3000)  // Initial delay phase
}

class BreathingStateManager {
    var currentPhase = mutableStateOf(BreathingPhase.PAUSE)
    var phaseProgress = mutableStateOf(0f)
    var isActive = mutableStateOf(true)
    var cycleCount = mutableStateOf(0)

    // Change to suspend function type
    private var onCycleCompleteAction: (suspend () -> Unit)? = null

    // Register suspend callback
    suspend fun onCycleComplete(action: suspend () -> Unit) {
        this.onCycleCompleteAction = action
    }

    suspend fun startBreathingCycle() {
        // Initial pause/get ready phase
        currentPhase.value = BreathingPhase.PAUSE
        animateProgress(BreathingPhase.PAUSE.duration)

        while (isActive.value) {
            // Inhale phase
            currentPhase.value = BreathingPhase.INHALE
            animateProgress(BreathingPhase.INHALE.duration)

            // Hold phase
            currentPhase.value = BreathingPhase.HOLD
            animateProgress(BreathingPhase.HOLD.duration)

            // Exhale phase
            currentPhase.value = BreathingPhase.EXHALE
            animateProgress(BreathingPhase.EXHALE.duration)

            cycleCount.value++

            // Callback after first complete cycle
            if (cycleCount.value == 1) {
                onCycleCompleteAction?.invoke()  // Call the suspend function
                break // Exit the loop after one cycle
            }
        }
    }

    private suspend fun animateProgress(duration: Int) {
        val stepTime = 16L // roughly 60fps
        val steps = duration / stepTime
        for (i in 0..steps) {
            phaseProgress.value = i.toFloat() / steps
            delay(stepTime)
        }
    }
}

@Composable
fun BreathingExercise(navController: NavController) {
    val breathingManager = remember { BreathingStateManager() }

    LaunchedEffect(Unit) {
        // Register the suspend callback
        breathingManager.onCycleComplete {
            // Navigation will automatically happen on Main thread
            navController.navigate(Screens.breathscreen4.route)
        }
        breathingManager.startBreathingCycle()
    }

    Image(
        painter = painterResource(R.drawable.starydarkbackground),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .scale(1.3f)
    )

    ConstraintLayout(Modifier.fillMaxSize()) {
        val bot = createRef()
        val loader = createRef()
        val text = createRef()

        BotwithUpperChat(
            text = "Let's take a deep breath together. Just like every road needs a smooth start, your mind needs a moment to reset.",
            modifier = Modifier
                .scale(1.05f, 1.15f)
                .constrainAs(bot) {
                    top.linkTo(parent.top, margin = 100.dp)
                    centerHorizontallyTo(parent)
                }
        )

        SynchronizedBreathingText(
            breathingManager = breathingManager,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(bot.bottom, margin = 20.dp)
                centerHorizontallyTo(parent)
            }
        )

        SynchronizedBreathingLoader(
            breathingManager = breathingManager,
            modifier = Modifier.constrainAs(loader) {
                top.linkTo(text.bottom, margin = 20.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}

@Composable
fun SynchronizedBreathingText(
    breathingManager: BreathingStateManager,
    modifier: Modifier = Modifier
) {
    val currentPhase by breathingManager.currentPhase
    SubHeadingText(currentPhase.displayText, modifier)
}

@Composable
fun SynchronizedBreathingLoader(
    breathingManager: BreathingStateManager,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.DarkGray,
    loaderColor: Color = Color.Yellow,
    loaderStrokeWidth: Float = 40f
) {
    val density = LocalDensity.current
    val containerHeight = with(density) { loaderStrokeWidth.toDp() }
    val cornerRadius = loaderStrokeWidth.dp / 2

    val currentPhase by breathingManager.currentPhase
    val phaseProgress by breathingManager.phaseProgress

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(containerHeight)
                .clip(RoundedCornerShape(cornerRadius))
                .border(width = 3.dp, color = Color.Black)
                .background(backgroundColor.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (currentPhase != BreathingPhase.PAUSE) {
                    val lineProgress = when (currentPhase) {
                        BreathingPhase.INHALE -> phaseProgress
                        BreathingPhase.HOLD -> 1f
                        BreathingPhase.EXHALE -> 1f - phaseProgress
                        else -> 0f
                    }
                    drawSynchronizedBreathingLine(
                        progress = lineProgress,
                        color = loaderColor,
                        strokeWidth = loaderStrokeWidth
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawSynchronizedBreathingLine(
    progress: Float,
    color: Color,
    strokeWidth: Float
) {
    val centerX = size.width / 2
    val centerY = size.height / 2

    val leftEndX = centerX - (centerX * progress)
    val rightEndX = centerX + (centerX * progress)

    drawLine(
        color = color,
        start = Offset(leftEndX, centerY),
        end = Offset(rightEndX, centerY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}