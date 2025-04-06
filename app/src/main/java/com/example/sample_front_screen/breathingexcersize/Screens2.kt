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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun breathscreen2(navController: NavController){
    Image(
        painter = painterResource(R.drawable.starydarkbackground),
        contentDescription = "Background",
        contentScale = ContentScale.Crop, // Ensures full coverage
        modifier = Modifier
            .fillMaxSize().scale(1.3f)

        // Ensures image fi
    )
    ConstraintLayout(Modifier.fillMaxSize()) {
        val text = createRef()
        SubHeadingText( text="On your Recovery Route,\n you might hit crossroads,\n climb steep mountains, or\n cross deep rivers, but the \nroad forward is always\n there.", modifier = Modifier.constrainAs(text){
            centerTo(parent)
        }.scale(1.3f)
        )
    }
    LaunchedEffect(Unit) {
        delay(1000)
        withContext(Dispatchers.Main) { // Switch to main thread
            navController.navigate(Screens.breathscreen3.route)
        }
    }
}