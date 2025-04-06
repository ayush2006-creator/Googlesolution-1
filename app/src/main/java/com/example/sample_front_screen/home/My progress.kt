package com.example.sample_front_screen.home
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Preview
@Composable
fun MyProgressScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Back and Menu icons


            Text(
                text = "MY PROGRESS",
                color = Color(0xFFFFFF00),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Top Row - Profile and Milestone
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFFFFF176), shape = RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "Name",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .background(Color(0xFF1A237E), shape = RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Text("LEVEL 1", color = Color(0xFFFFC107), fontWeight = FontWeight.Bold)
                    Text("WANDERER", color = Color(0xFFFFA000), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("(Just stepped onto the path)", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 1 Day Streak
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Whatshot, contentDescription = null, tint = Color(0xFFFF5722), modifier = Modifier.size(60.dp))
                Text("1 DAY STREAK", color = Color(0xFFFF9800), fontWeight = FontWeight.Bold)
            }

            // Calendar Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFFFF176), shape = RoundedCornerShape(16.dp))
            ) {
                Text("JAN 20XX", modifier = Modifier.align(Alignment.TopCenter).padding(8.dp), fontWeight = FontWeight.Bold)
                // Replace this with your own calendar UI
            }

            // Money saved
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFF8D), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text("You have saved ₹0", fontWeight = FontWeight.Bold)
            }

            // Timer Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A237E), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "01 Days 22 Hours 99 Minutes 10 Seconds",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            // Circular progress
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    progress = 0.25f,
                    color = Color(0xFF1A237E),
                    strokeWidth = 8.dp,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Milestones Achieved", color = Color(0xFFFFA000), fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun CalendarGrid(markedDays: List<Int>) {
    // Get current date information
    val currentDate = remember { Calendar.getInstance() }
    val currentMonth = currentDate.get(Calendar.MONTH)
    val currentYear = currentDate.get(Calendar.YEAR)

    // Calculate days in current month
    val daysInMonth = remember {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, currentYear)
        cal.set(Calendar.MONTH, currentMonth)
        cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    // Calculate first day of month (0 = Sunday, 1 = Monday, etc.)
    val firstDayOfMonth = remember {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, currentYear)
        cal.set(Calendar.MONTH, currentMonth)
        cal.set(Calendar.DAY_OF_MONTH, 1)
        // Convert to Monday-based (0 = Monday, 6 = Sunday)
        (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
    }

    // Get month name
    val monthName = remember {
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
        monthFormat.format(currentDate.time).uppercase()
    }

    val daysOfWeek = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFF8D), shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        // Month and year header
        Text(
            text = "$monthName $currentYear",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Days of week header
        Row(Modifier.fillMaxWidth()) {
            daysOfWeek.forEach {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Create calendar data with empty spaces for alignment
        val calendarDays = List(firstDayOfMonth) { 0 } + (1..daysInMonth).toList()
        val calendarWeeks = calendarDays.chunked(7)

        // Calendar grid
        calendarWeeks.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                for (i in 0 until 7) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (i < week.size && week[i] != 0) {
                            val day = week[i]
                            val isMarked = markedDays.contains(day)

                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        if (isMarked) Color(0xFFFF7043) else Color.Transparent,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isMarked) "🔥" else day.toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isMarked) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun calender1(){


CalendarGrid(markedDays = listOf(2, 3, 4, 5, 6, 20, 25))}

