package com.example.sample_front_screen.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.ui.theme.DarkYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun NewPostScreen(navController: NavController) {
    var displayName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("Motivation", "Cravings", "Success Stories", "Quit Tips", "Journey Start")
    Image(
        painter = painterResource(R.drawable.frame_164__1_),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(top =30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("◀ New post", color = Color(0xFFFFB300), fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navController.navigate(
                Screens.community.route) })
            Text("Submit", color = Color(0xFFFFB300), fontWeight = FontWeight.Bold,modifier = Modifier.clickable {  })
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display Name Input
        OutlinedTextField(
            value = displayName,
            onValueChange = {
                if (it.length <= 50) displayName = it
            },
            placeholder = {
                Text("Display Name", color = Color(0xFFFFB300))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFB300),
                unfocusedBorderColor = Color(0xFFFFB300),
                cursorColor = Color(0xFFFFB300),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            "${displayName.length}/50",
            color = Color(0xFFFFB300),
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Dropdown Menu


        CategoryDropdown1()
        Spacer(modifier = Modifier.height(50.dp))

        // Message Box
        OutlinedTextField(
            value = message,
            onValueChange = {
                if (it.length <= 350) message = it
            },
            placeholder = {
                Text("Message", color = Color(0xFFFFB300))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFB300),
                unfocusedBorderColor = Color(0xFFFFB300),
                cursorColor = Color(0xFFFFB300),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            maxLines = 10
        )

        Text(
            "${message.length}/350",
            color = Color(0xFFFFB300),
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun CategoryDropdown1() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        // Custom clickable field that looks like TextField but has better control
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFFFB300),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { expanded = true }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedCategory.ifEmpty { "Select Category" },
                    color = if (selectedCategory.isEmpty()) Color(0xFFFFB300) else Color.White
                )
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = Color(0xFFFFB300)
                )
            }
        }

        // Dropdown with proper anchoring
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color(0xFFFDC42C))
        ) {
            val categories = listOf("MOTIVATION", "CRAVINGS", "SUCCESS STORIES", "QUIT TIPS", "STARTING THE JOURNEY")

            categories.forEach { category ->
                val isSelected = category == selectedCategory

                DropdownMenuItem(
                    text = {
                        Text(
                            text = category,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (isSelected) Color.White else Color.Black,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    },
                    onClick = {
                        selectedCategory = category
                        coroutineScope.launch {
                            delay(250)
                            expanded = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isSelected) DarkYellow else Color.Transparent,

                        )
                )
            }
        }
    }
}


