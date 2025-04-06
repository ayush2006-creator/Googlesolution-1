package com.example.sample_front_screen.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.reusablefunctions.Botwithsidechat
import com.example.sample_front_screen.ui.theme.SignYellow

@Composable
fun EnemyChoose(navController: NavController) {
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

        Botwithsidechat(
            text = "Warrior! Choose all enemies you want to fight.",
            modifier = Modifier.constrainAs(bot) {
                top.linkTo(parent.top, margin = (-90).dp)
                start.linkTo(parent.start, margin = (-110).dp)
            }
        )
        val selectedOptions = remember { mutableStateListOf<String>() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(grid) {
                    top.linkTo(bot.bottom, margin = 30.dp)
                }
                .scale(1.1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // First Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectionCard(
                    iconRes = R.drawable.drugs,
                    text = "DRUGS",
                    isSelected = selectedOptions.contains("DRUGS"),
                    onSelectionChanged = { isSelected ->
                        if (isSelected) selectedOptions.add("DRUGS")
                        else selectedOptions.remove("DRUGS")
                    }
                )
                SelectionCard(
                    iconRes = R.drawable.vape,
                    text = "VAPING",
                    isSelected = selectedOptions.contains("VAPING"),
                    onSelectionChanged = { isSelected ->
                        if (isSelected) selectedOptions.add("VAPING")
                        else selectedOptions.remove("VAPING")
                    }
                )
            }

            // Second Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectionCard(
                    iconRes = R.drawable.bottle,
                    text = "ALCOHOL",
                    isSelected = selectedOptions.contains("ALCOHOL"),
                    onSelectionChanged = { isSelected ->
                        if (isSelected) selectedOptions.add("ALCOHOL")
                        else selectedOptions.remove("ALCOHOL")
                    }
                )
                SelectionCard(
                    iconRes = R.drawable.smoke,
                    text = "SMOKING",
                    isSelected = selectedOptions.contains("SMOKING"),
                    onSelectionChanged = { isSelected ->
                        if (isSelected) selectedOptions.add("SMOKING")
                        else selectedOptions.remove("SMOKING")
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(grid.bottom, margin = 75.dp)
                    centerHorizontallyTo(parent)
                }
                .height(50.dp)
                .width(300.dp),
            onClick = {
                // Handle selected options here
                navController.navigate(Screens.expenditure.route)
                println("Selected enemies: ${selectedOptions.joinToString()}")
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
fun SelectionCard(
    iconRes: Int,
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .height(40.dp)
            .clickable { onSelectionChanged(!isSelected) },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else Color(0x19D4D4D4),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = "$text Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}