package com.example.sample_front_screen.tasks

import AnimatedSideMenu
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.fontfunctions.HeadingText
import com.example.sample_front_screen.fontfunctions.SubHeadingText
import com.example.sample_front_screen.ui.theme.DarkYellow
import com.example.sample_front_screen.ui.theme.SignYellow
import com.example.sample_front_screen.ui.theme.Yellow
import kotlin.math.roundToInt



@Composable
fun MoodSelectorScreen(navController:NavController) {
    val moods = listOf(
        "😫" to "DE-MOTIVATED",
        "😔" to "SAD & DEPRESSED",
        "😐" to "OKAY",
        "😄" to "HAPPY",
        "😍" to "MOTIVATED"
    )
    var isMenuOpen by remember { mutableStateOf(false) }

    var sliderPosition by remember { mutableStateOf(2f) } // Default to "OKAY"
    val (emoji, label) = moods[sliderPosition.roundToInt()]
    Image(
        painter = painterResource(R.drawable.frame_164__1_),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()

    )

    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val back = createRef()
            val menu = createRef()
            val heading = createRef()

            FilledIconButton(
                onClick = {isMenuOpen = !isMenuOpen},
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top, margin = 20.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                    }
                    .scale(scaleX = 1.5f ,scaleY=1F),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.White.copy(alpha = 0.3f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(40),

                ) {
                Icon(
                    painter = painterResource(R.drawable.menum),
                    contentDescription = "menu"
                )
            }
            FilledIconButton(
                onClick = {navController.navigate(Screens.dashboard1.route) },
                modifier = Modifier
                    .constrainAs(menu) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                    }
                    .scale(scaleX = 1.5f ,scaleY=1F),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.White.copy(alpha = 0.3f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(40),

                ) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back button"
                )
            }
            SubHeadingText("Hows today?",  Modifier.constrainAs(heading){
                top.linkTo(back.bottom, margin = 30.dp)
                centerHorizontallyTo(parent)
            }.scale(1.8f))
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header with Back and Menu icons





            Spacer(Modifier.height(50.dp))
            Text(
                text = emoji,
                fontSize = 110.sp
            )

            Text(
                text = label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color =     DarkYellow
            )




            // Gradient slider
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..4f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFFFC107),
                    activeTrackColor = Yellow,
                    inactiveTrackColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(0.9f)
            )



            Button(
                modifier = Modifier

                    .height(50.dp)
                    .width(300.dp),
                onClick = {
                    // Handle selected options here

                },
                elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SignYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Select")
            }
        }
    }
    AnimatedSideMenu(isMenuOpen, navController) { isMenuOpen = false }
}
