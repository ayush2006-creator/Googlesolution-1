package com.example.sample_front_screen.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.example.sample_front_screen.R
import com.example.sample_front_screen.ui.theme.SignYellow

@Preview
@Composable
fun introScreen(){
    Image(
        painter = painterResource(R.drawable.frame_191),
        contentDescription = "Background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()

    )
    ConstraintLayout(Modifier.fillMaxSize()) {
        val button = createRef()
    Button(
        modifier = Modifier
            .constrainAs(button){
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom,margin = 30.dp)
            }

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
    }}
}