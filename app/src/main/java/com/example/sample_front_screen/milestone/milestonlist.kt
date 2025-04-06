package com.example.sample_front_screen.milestone

import AnimatedSideMenu
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.R
import com.example.sample_front_screen.fontfunctions.HeadingText
import com.example.sample_front_screen.fontfunctions.SubHeadingText

@Preview
@Composable
fun milestonelist(){
    var isMenuOpen by remember { mutableStateOf(false) }
    Image(
        painter = painterResource(R.drawable.frame_164__1_),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    ConstraintLayout(Modifier.fillMaxSize()) {
        val back = createRef()
        val menu = createRef()
        val heading = createRef()
        val subheading = createRef()
        val grid = createRef()
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
            onClick = { },
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
        HeadingText("Milestones Achieved",  Modifier.constrainAs(heading){
            top.linkTo(back.bottom, margin = 20.dp)
            centerHorizontallyTo(parent)
        }.scale(0.85f))
        SubHeadingText("Many more Awaiting...",Modifier.constrainAs(subheading){
            top.linkTo(heading.bottom, margin = (-10.dp))
            centerHorizontallyTo(parent)
        }.scale(0.95f))



    }

    AnimatedSideMenu(isMenuOpen) { isMenuOpen = false }
    }
