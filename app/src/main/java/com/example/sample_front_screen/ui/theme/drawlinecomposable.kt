package com.example.sample_front_screen.ui.theme

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun NearestNeighborLines(modifier: Modifier = Modifier) {
    var points by remember {
        mutableStateOf(
            listOf(
                mutableStateOf(PointF(100f, 200f)),
                mutableStateOf(PointF(200f, 300f)),
                mutableStateOf(PointF(300f, 100f)),
                mutableStateOf(PointF(400f, 500f)),
                mutableStateOf(PointF(500f, 400f)),
                mutableStateOf(PointF(600f, 200f)),
                mutableStateOf(PointF(700f, 300f)),
                mutableStateOf(PointF(800f, 100f))
            )
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val connectedPairs = mutableSetOf<Pair<PointF, PointF>>()

        for (point in points) {
            val nearestNeighbors = points
                .map { it.value }
                .filter { it != point.value }
                .sortedBy { distance(point.value, it) }
                .take(4) // Get 4 nearest neighbors

            for (neighbor in nearestNeighbors) {
                val pair = if (point.value < neighbor) point.value to neighbor else neighbor to point.value
                if (pair !in connectedPairs) {
                    drawLineBetweenPoints(point.value, neighbor)
                    connectedPairs.add(pair)
                }
            }
        }
    }

    // Overlay draggable points
    points.forEach { pointState ->
        DraggablePoint(pointState)
    }
}

// Function to draw a line between two points
private fun DrawScope.drawLineBetweenPoints(p1: PointF, p2: PointF) {
    drawLine(
        color = Color.Black,
        start = Offset(p1.x, p1.y),
        end = Offset(p2.x, p2.y),
        strokeWidth = 4f
    )
}

// Function to calculate Euclidean distance
private fun distance(p1: PointF, p2: PointF): Float {
    return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
}

// Comparison operator to avoid duplicate pairs
private operator fun PointF.compareTo(other: PointF): Int {
    return if (x != other.x) x.compareTo(other.x) else y.compareTo(other.y)
}

@Composable
fun DraggablePoint(pointState: MutableState<PointF>) {
    Box(
        modifier = Modifier
            .offset {
                androidx.compose.ui.unit.IntOffset(pointState.value.x.toInt(), pointState.value.y.toInt())
            }
            .size(20.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    pointState.value = PointF(
                        pointState.value.x + dragAmount.x,
                        pointState.value.y + dragAmount.y
                    )
                }
            }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewNearestNeighborLines() {
    NearestNeighborLines(modifier = Modifier.fillMaxSize().padding(16.dp))
}
