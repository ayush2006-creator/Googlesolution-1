package com.example.sample_front_screen.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.reusablefunctions.Botwithsidechat
import com.example.sample_front_screen.ui.theme.SignYellow


@Composable
fun Expenditure(navController: NavController) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Image(
        painter = painterResource(R.drawable.animaatedroadbackground),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .scale(1.3f)
    )

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (bot, grid,button) = createRefs()

        Botwithsidechat(
            text = "How much money do you spend on it weekly?",
            modifier = Modifier.constrainAs(bot) {
                top.linkTo(parent.top, margin = (-85).dp)
                start.linkTo(parent.start, margin = (-120).dp)
            }
        )

        val items = listOf(
            "LESS THAN \u20B9100",
            "₹100 - 500",
            "₹500 - 2000",
            "₹2000 - 8000",
            "₹8000 - 10000",
            "MORE THAN ₹10000"
        )

        Column(
            modifier = Modifier
                .constrainAs(grid) {
                    top.linkTo(bot.bottom, margin = -30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEach { item ->
                ExpenditureOption(
                    text = item,
                    isSelected = selectedOption == item,
                    onSelected = { selectedOption = item }
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
                navController.navigate(Screens.triggers.route)
                // Handle selected options here

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
fun ExpenditureOption(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(230.dp)  // Wider for better text display
            .height(40.dp)  // Slightly taller
            .clickable { onSelected() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Gray else Color(0x19D4D4D4),
            contentColor = if (isSelected) Color.White else Color.White
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.W900,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
        }
    }
}