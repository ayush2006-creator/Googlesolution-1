package com.example.sample_front_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout

@Preview
@Composable
fun DirectionSelector(){
    Image(
        painter = painterResource(R.drawable.letsdoit),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    ConstraintLayout(modifier = Modifier.fillMaxSize()){
        val (assistant) = createRefs()
        val (board) = createRefs()
        val (button)=createRefs()
        val(question)=createRefs()

        val vector = ImageVector.vectorResource(id = R.drawable.frame_43)
        Image(imageVector = vector, contentDescription = "Vector",modifier = Modifier.constrainAs(assistant){
            top.linkTo(parent.top, margin = 45.dp)
            start.linkTo(parent.start, margin = 30.dp)


        }.scale(1.13f))


        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .padding(25.dp)
                .constrainAs(button){
                    bottom.linkTo(parent.bottom, margin = 45.dp)
                }
            ,
            colors = ButtonColors(
                contentColor = Color.Black, containerColor = Color(0xFFFDC42C),
                disabledContainerColor = Color(0xFFFDC42C),
                disabledContentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp,   // Normal elevation
                pressedElevation = 2.dp,   // Elevation when pressed

                focusedElevation = 10.dp   // When focused

            ) ) {
            Text(
                text = "NEXT",
                fontSize = 18.sp,  // ✅ Correct font size
                fontWeight = FontWeight.Bold, // Optional for emphasis
                textAlign = TextAlign.Center, // Ensures text is centered
                fontFamily = FontFamily(Font(R.font.lato)),
                modifier = Modifier.padding(5.dp)

            )
        }
        val vector2 = ImageVector.vectorResource(id = R.drawable.signboardd)
        Image(imageVector = vector2, contentDescription = "Vector",
            modifier = Modifier.constrainAs(board){
                bottom.linkTo(button.top, margin = ((-15).dp))
                centerHorizontallyTo(parent)
            }.scale(1.1f).zIndex(0f))


        MultiCorrectQuestionInterface(options = listOf("DRUGS","SMOKING","ALCOHOL","VAPE"), modifier = Modifier.constrainAs(question){
            top.linkTo(board.top)
            start.linkTo(parent.start, margin = ((-37).dp))
        }.scale(0.6f))



}}

@Composable
fun MultiCorrectQuestionInterface(

    options: List<String>,
    modifier: Modifier = Modifier
) {
    val selectedOptions = remember { mutableStateListOf<Int>() }
    val typedAnswer = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        // 2x2 Grid of options
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(options) { index, option ->
                val isSelected = selectedOptions.contains(index)

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(50.dp)

                        .clickable {
                            if (isSelected) {
                                selectedOptions.remove(index)
                            } else {
                                selectedOptions.add(index)
                            }
                        },
                    colors = CardColors(containerColor = Color(0xFF2261DA), contentColor = Color.White, disabledContentColor =  Color.White, disabledContainerColor = Color(0xFF2261DA))



                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Bullet indicator that changes when selected
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .border(
                                        width = 2.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                        shape = CircleShape
                                    )
                                    .padding(2.dp)
                                    .background(
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        shape = CircleShape
                                    )
                            )

                            Text(
                                text = option,
                                fontSize = 23.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }



        // Type your answer section
        Row(
            modifier = Modifier.fillMaxWidth(0.7f),

        ) {
            Text(
                modifier = Modifier.padding(top = 15.dp, start = 15.dp),
                text = "Others",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 22.sp


            )

            TextField(
                value = typedAnswer.value,
                onValueChange = { typedAnswer.value = it },
                modifier = Modifier
                    .fillMaxWidth(),

                textStyle = MaterialTheme.typography.bodyLarge,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )
        }



        // Submit button

    }
}

