package com.example.sample_front_screen.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.reusablefunctions.Botwithsidechat
import com.example.sample_front_screen.ui.theme.SignYellow


@Composable
fun TriggerPoints(navController: NavController) {
    Image(
        painter = painterResource(R.drawable.animaatedroadbackground),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .scale(1.3f)
    )

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (bot, grid, button) = createRefs()
        val selectedOptions = remember { mutableStateListOf<String>() }

        Botwithsidechat(
            text = "Warrior! Choose all enemies you want to fight.",
            modifier = Modifier.constrainAs(bot) {
                top.linkTo(parent.top, margin = (-90).dp)
                start.linkTo(parent.start, margin = (-110).dp)
            }
        )

        val items = listOf(
            "Down time or in between activities",
            "Being on phone",
            "Drinking coffee",
            "Finishing a meal",
            "Seeing visuals on TV or movies",
            "Waiting for a ride",
            "Working or studying",
            "Going to a party or other event",
            "Being offered by a friend",
            "Certain people"
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(0.75f)
                .constrainAs(grid) {
                    top.linkTo(bot.bottom, margin = 0.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(400.dp),  // Fixed height for scrollable area
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.Center,
            userScrollEnabled = true
        ) {
            items(items.size) { index ->
                val item = items[index]
                TriggerPointCard(
                    text = item,
                    isSelected = selectedOptions.contains(item),
                    onSelectionChanged = { selected ->
                        if (selected) {
                            if (!selectedOptions.contains(item)) {
                                selectedOptions.add(item)
                            }
                        } else {
                            selectedOptions.remove(item)
                        }
                    }
                )
            }
        }
        Button(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(grid.bottom, margin = 30.dp)
                    centerHorizontallyTo(parent)
                }
                .height(50.dp)
                .width(300.dp),
            onClick = {
                // Handle selected options here
                navController.navigate(Screens.breathscreen1.route)


            },
            elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SignYellow,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Next")
        }
    }
}

@Composable
fun TriggerPointCard(
    text: String,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier

            .height(40.dp)  // Taller for better touch targets
            .clickable { onSelectionChanged(!isSelected) },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else Color(0x19D4D4D4),
            contentColor = if (isSelected) Color.White else Color.White
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()

                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(

                text = text,
                fontWeight = FontWeight.W900,

                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),

                textAlign = TextAlign.Center

            )
        }
    }
}