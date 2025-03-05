package com.example.sample_front_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.ui.theme.SignYellow
import com.example.sample_front_screen.ui.theme.Yellow

@Preview
@Composable
fun LetsDoIt() {
    Image(
        painter = painterResource(R.drawable.letsdoit),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (button) = createRefs()
        val (sign) = createRefs()
        Image(painter = painterResource(R.drawable.sign),
            contentDescription = "Background",

            modifier = Modifier.size(500.dp).constrainAs(sign) {
                top.linkTo(parent.top, margin = 200.dp)

            })
        Button(modifier = Modifier.constrainAs(button){
            top.linkTo(sign.bottom, margin = (-12.dp))
            start.linkTo(parent.start, margin = 38.dp)

        }
            .width(340.dp)
            , onClick = {},elevation=ButtonDefaults.elevatedButtonElevation(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor =SignYellow, // Background color
                contentColor = Color.Black  // Text color
            ),
            shape = RoundedCornerShape(8.dp)
        )
        { Text("YES LET'S DO IT")}


    }
}

