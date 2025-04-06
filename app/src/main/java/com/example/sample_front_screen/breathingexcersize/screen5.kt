package com.example.sample_front_screen.breathingexcersize



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.fontfunctions.HeadingText
import com.example.sample_front_screen.fontfunctions.SubHeadingText
import kotlinx.coroutines.delay


@Composable
fun breathscreen5(navController: NavController){
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
        val text2 = createRef()
        SubHeadingText( text="JUST TRUST THIS JOURNEY TO", modifier = Modifier.constrainAs(text){
            centerTo(parent)
        }.scale(1.3f)
        )
        HeadingText("RECOVERY",Modifier.constrainAs(text2){
            top.linkTo(text.bottom, margin = 20.dp)
            centerHorizontallyTo(parent)
        })
    }
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate(Screens.lighttoweronboard.route)

    }
}