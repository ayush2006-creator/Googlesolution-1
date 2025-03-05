package com.example.sample_front_screen

import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sample_front_screen.ui.theme.DarkYellow
import com.example.sample_front_screen.ui.theme.Red
import com.example.sample_front_screen.ui.theme.Yellow
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun alternateWordLayout(words: List<String>, screenWidth: Float,screenHeight: Float,colorset:Int,context:Context): SnapshotStateList<String> {
    // Calculate spacing based on screen width
    val wordWidth = screenWidth * 0.3f
    val horizontalSpacing = screenWidth * 0.12f
    val verticalSpacing = screenWidth * 0.06f
    val collectedWords = remember { mutableStateListOf<String>() }
    val color: Array<Color> = when (colorset) {
        1 -> {
            arrayOf(Yellow, Red, DarkYellow, Yellow)
        }
        2 -> {
            arrayOf(DarkYellow, Red, Yellow, Red)
        }
        else -> {
            arrayOf(Red, DarkYellow, Yellow, Red)
        }
    }



    Column(
        verticalArrangement = Arrangement.spacedBy(verticalSpacing.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy((horizontalSpacing * 0.3f).dp)
        ) {
            val visibilityState = remember {
                mutableStateListOf<Boolean>().apply {
                    // Initialize all words as visible
                    repeat(words.size) { add(true) }

                }
            }

            words.forEachIndexed { index, word ->
                if (index % 2 == 1) {
                    val x = (index / 2) * (wordWidth + horizontalSpacing * 0.9f)


// State for the list of collected words

                    val density = LocalDensity.current
                    val y = with(density) { verticalSpacing.dp.toPx() }

                    // Create multiple animated values for more complex motion
                    val infiniteTransition = rememberInfiniteTransition()
                    val interactionSource = remember { MutableInteractionSource() }

                    // First random motion component
                    val xMotion1 by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 2000 + Random.nextInt(2000),
                                easing = FastOutSlowInEasing
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Second random motion component (different timing)
                    val xMotion2 by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 3000 + Random.nextInt(1500),
                                easing = LinearOutSlowInEasing
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Y motion components (different timing from X)
                    val yMotion1 by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 2500 + Random.nextInt(1800),
                                easing = CubicBezierEasing(0.2f, 0.8f, 0.4f, 1.0f)
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Rotation animation (optional - makes the motion appear more chaotic)
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 8000 + (index * 1000),
                                easing = LinearEasing
                            )
                        )
                    )

                    // Generate random range for this word
                    val xRange = remember { 60f + Random.nextFloat() * 80f }
                    val yRange = remember { 40f + Random.nextFloat() * 60f }

                    // Combine motion components with different weights
                    val animatedPosition = remember(xMotion1, xMotion2, yMotion1, rotation) {
                        // Create a semi-random motion path by combining multiple animations
                        val xOffset = (xMotion1 * 0.6f + sin(rotation * 0.017f) * 0.4f) * xRange*0.9f
                        val yOffset = (yMotion1 * 0.7f + cos(rotation * 0.017f) * 0.3f) * yRange
                        val rawX = x + xOffset - xRange / 2
                        val rawY = y + yOffset - yRange / 2
                        val constrainedX = rawX.coerceIn(0f, screenWidth)
                        val constrainedY = rawY.coerceIn(0f, screenHeight)
                        Offset(
                            x = constrainedX,
                            y = constrainedY
                        )
                    }


                    if (visibilityState.getOrNull(index) == true) {

                        Text(
                            text = word,
                            modifier = Modifier
                                .width(wordWidth.dp)
                                .offset(
                                    x = with(density) { animatedPosition.x.toDp() },
                                    y = with(density) { animatedPosition.y.toDp() }
                                )
                                .clickable(


                                    onClick = {
                                        visibilityState[index] = false


                                        collectedWords.add(word)
                                        playSound(context,R.raw.pop)


                                    }),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace,
                            fontSize = (screenWidth * 0.04f).sp,
                            color = color[index]
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(start = (horizontalSpacing * 1.8f).dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy((horizontalSpacing*0.7f).dp)
        ) {
            val visibilityState = remember {
                mutableStateListOf<Boolean>().apply {
                    // Initialize all words as visible
                    repeat(words.size) { add(true) }

                }
            }

            words.forEachIndexed { index, word ->
                if (index % 2 == 0) {
                    val x = (index / 2) * (wordWidth + horizontalSpacing * 0.9f)


// State for the list of collected words

                    val density = LocalDensity.current
                    val y = with(density) { verticalSpacing.dp.toPx() }

                    // Create multiple animated values for more complex motion
                    val infiniteTransition = rememberInfiniteTransition()
                    val interactionSource = remember { MutableInteractionSource() }

                    // First random motion component
                    val xMotion1 by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 2000 + Random.nextInt(2000),
                                easing = FastOutSlowInEasing
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Second random motion component (different timing)
                    val xMotion2 by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 3000 + Random.nextInt(1500),
                                easing = LinearOutSlowInEasing
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Y motion components (different timing from X)
                    val yMotion1 by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 2500 + Random.nextInt(1800),
                                easing = CubicBezierEasing(0.2f, 0.8f, 0.4f, 1.0f)
                            ),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Rotation animation (optional - makes the motion appear more chaotic)
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 8000 + (index * 1000),
                                easing = LinearEasing
                            )
                        )
                    )

                    // Generate random range for this word
                    val xRange = remember { 60f + Random.nextFloat() * 80f }
                    val yRange = remember { 40f + Random.nextFloat() * 60f }

                    // Combine motion components with different weights
                    val animatedPosition = remember(xMotion1, xMotion2, yMotion1, rotation) {
                        // Create a semi-random motion path by combining multiple animations
                        val xOffset = (xMotion1 * 0.6f + sin(rotation * 0.017f) * 0.4f) * xRange*0.5f
                        val yOffset = (yMotion1 * 0.7f + cos(rotation * 0.017f) * 0.3f) * yRange
                        val rawX = x + xOffset - xRange / 2
                        val rawY = y + yOffset - yRange / 2
                        val constrainedX = rawX.coerceIn(0f, screenWidth)
                        val constrainedY = rawY.coerceIn(0f, screenHeight)
                        Offset(
                            x = constrainedX,
                            y = constrainedY
                        )
                    }
                    if (visibilityState.getOrNull(index) == true) {

                        Text(
                            text = word,
                            modifier = Modifier
                                .width(wordWidth.dp)
                                .offset(
                                    x = with(density) { animatedPosition.x.toDp() },
                                    y = with(density) { animatedPosition.y.toDp() }
                                )
                                .clickable(

                                    onClick = {
                                        visibilityState[index] = false


                                        collectedWords.add(word)
                                        playSound(context,R.raw.pop)


                                    }),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace,
                            fontSize = (screenWidth * 0.04f).sp,
                            color = color[index]
                        )
                    }
                }
            }
        }
    }
    return collectedWords
}




@Composable
fun PreviewWordGrid(context: Context) {
    // Get screen dimensions
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.toFloat()
    val screenHeight = configuration.screenHeightDp.toFloat()
    val collectedword: MutableList<String> = mutableListOf()


    val words = listOf(
        "Depression", "Suffering", "Shame","Weakness", "Fear",
        "Relapse", "Trouble", "Anxiety", "Urge",
        "Struggle", "Loss", "Stress", "Loss",
        "Anxiety", "Hunger", "Shame", "Relapse",
        "Anxiety", "Craving", "Isolation",
        "Hopelessness", "Stress", "Regret", "Struggle",
        "Confusion", "Desperation", "Trouble", "Anger",
        "Obsession", "Frustration",  "Panic","Temptation"
    )

    val totalChunks = words.chunked(4)
    val middleIndex = totalChunks.size / 2
    val topChunks = totalChunks.take(middleIndex)
    val bottomChunks = totalChunks.drop(middleIndex)

    Scaffold(
        containerColor = Color.Black
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxSize()
                .background(Color.Black)
                .clipToBounds()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top section
                Column(
                    modifier = Modifier.weight(3f),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    topChunks.forEachIndexed { index,chunk ->
                        val colorset:Int = if (index%3==0){
                            0
                        } else if (index<2){
                            2
                        } else{
                            1
                        }
                        alternateWordLayout(chunk, screenWidth,screenHeight,colorset,context)
                    }
                }

                // Middle image section
                Box(
                    modifier = Modifier

                        .weight(0.5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.addiction),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .aspectRatio(1f)

                    )
                }

                // Bottom section

                Column(
                    modifier = Modifier.weight(3f),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    bottomChunks.forEachIndexed { index,chunk ->
                        val colorset:Int = if (index%3==0){
                            0
                        } else if (index<2){
                            2
                        } else{
                            1
                        }
                       alternateWordLayout(chunk, screenWidth,screenHeight,colorset, context)

                    }
                }
            }
        }

    }
}