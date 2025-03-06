package com.example.sample_front_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.ui.theme.SignYellow

@Preview
@Composable
fun WeeklySpent(){
    Image(
        painter = painterResource(R.drawable.letsdoit),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (assistant)=createRefs()
        val (board)=createRefs()
        val vector = ImageVector.vectorResource(id = R.drawable.assisstantspent)
        val (rod1) = createRefs()
        val (rod2)=createRefs()
        val(button)=createRefs()
        val vectorrod = ImageVector.vectorResource(id = R.drawable.rod)

        Image(imageVector = vector, contentDescription = "Vector",modifier = Modifier.constrainAs(assistant){
            top.linkTo(parent.top, margin = 45.dp)
            start.linkTo(parent.start, margin = 30.dp)


        }.scale(1.13f))
        Image(imageVector = vectorrod, contentDescription = "Vector",modifier = Modifier.constrainAs(rod1){
            bottom.linkTo(parent.bottom, margin = 0.dp)
            centerHorizontallyTo(parent,bias =0.3f)


        }.scale(1.5f,2f))
        Image(imageVector = vectorrod, contentDescription = "Vector",modifier = Modifier.constrainAs(rod2){
            bottom.linkTo(parent.bottom, margin = 0.dp)
            centerHorizontallyTo(parent,bias =0.7f)


        }.scale(1.5f,2f))
        Button(modifier = Modifier.constrainAs(button){
            bottom.linkTo(parent.bottom, margin = (35.dp))
            centerHorizontallyTo(parent)

        }
            .width(340.dp)
            , onClick = {},elevation= ButtonDefaults.elevatedButtonElevation(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = SignYellow, // Background color
                contentColor = Color.Black  // Text color
            ),
            shape = RoundedCornerShape(8.dp)
        )
        { Text("NEXT")}

    Column(
        modifier = Modifier.constrainAs(board) {
            centerHorizontallyTo(parent)
            bottom.linkTo(parent.bottom, margin = 140.dp)
        },
        verticalArrangement = Arrangement.spacedBy(20.dp),

    ) {
        val item = listOf("LESS THAN \u20B9100","₹100 - 500","₹500 -2000","₹2000 - 8000","₹8000 - 10000","MORE THAN ₹10000")
        item.forEach { it ->
            Box(
                modifier = Modifier
                    .background(Color(0xFF2261DA)) // Blue background with rounded corners
                    .width(300.dp)
                    .border(2.dp, Color.White) ,// White border

                contentAlignment = Alignment.CenterStart // Center the content
            ) {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (text)=createRefs()
                    val (image)=createRefs()
                    Text(it, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.constrainAs(text){
                        start.linkTo(parent.start, margin = 10.dp)
                        centerVerticallyTo(parent)
                    })
                    Image(
                        painter = painterResource(id = R.drawable.vector_21), // Replace with your PNG resource
                        contentDescription = "Clickable Image",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable (onClick={},interactionSource = remember { MutableInteractionSource() }, // Prevents default ripple
                                indication = null)
                            .constrainAs(image){
                                end.linkTo(parent.end, margin = 0.dp)
                                top.linkTo(parent.top, margin =  10.dp)
                            }

                    )

                }
            }
        }
    }

}}


