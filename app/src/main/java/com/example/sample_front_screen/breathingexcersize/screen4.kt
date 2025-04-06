package com.example.sample_front_screen.breathingexcersize

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.fontfunctions.SubHeadingText
import kotlinx.coroutines.delay


@Composable
fun breathscreen4(navController: NavController){
    Image(
        painter = painterResource(R.drawable.starydarkbackground),
        contentDescription = "Background",
        contentScale = ContentScale.Crop, // Ensures full coverage
        modifier = Modifier
            .fillMaxSize()

        // Ensures image fi
    )
    ConstraintLayout(Modifier.fillMaxSize()) {
        val text = createRef()
        SubHeadingText( text="GOOD JOB", modifier = Modifier.constrainAs(text){
            centerTo(parent)
        }.scale(1.3f)
        )
    }
    LaunchedEffect(Unit) {
        delay(20)
        navController.navigate(Screens.breathscreen5.route)

    }
}