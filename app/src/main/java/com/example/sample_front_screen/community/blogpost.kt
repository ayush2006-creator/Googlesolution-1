package com.example.sample_front_screen.community

import AnimatedSideMenu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.fontfunctions.HeadingText
import com.example.sample_front_screen.fontfunctions.SubHeadingText
import com.example.sample_front_screen.ui.theme.DarkYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun header(navController: NavController){
    var isMenuOpen by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(R.drawable.frame_164__1_),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()

    )
    Column {
        ConstraintLayout() {
            val back = createRef()
            val menu = createRef()
            val heading = createRef()
            val subheading = createRef()

            FilledIconButton(
                onClick = { isMenuOpen = !isMenuOpen },
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top, margin = 20.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                    }
                    .scale(scaleX = 1.5f, scaleY = 1F),
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
                onClick = { navController.navigate(Screens.dashboard1.route)},
                modifier = Modifier
                    .constrainAs(menu) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                    }
                    .scale(scaleX = 1.5f, scaleY = 1F),
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
            HeadingText("CROSSROADS COLLECTIVE", Modifier.constrainAs(heading) {
                top.linkTo(back.bottom, margin = 15.dp)
                centerHorizontallyTo(parent)
            }.fillMaxWidth().scale(0.75f), fontsize = 30.00)
            SubHeadingText("New post +", modifier = Modifier.constrainAs(subheading){
                top.linkTo(heading.bottom, margin = (-5.dp))
                end.linkTo(heading.end, margin = 40.dp)
            }.clickable { navController.navigate(Screens.new.route) }.scale(0.9f))


        }


        Spacer(modifier = Modifier.height(16.dp))
        CategoryDropdown()
        Spacer(modifier = Modifier.height(16.dp))
        BlogPostList()

    }
    AnimatedSideMenu(isMenuOpen, navController = navController) { isMenuOpen = false }
}

@Composable
fun CategoryDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("MOTIVATION") }

    Box() {
        Button(
            onClick = { expanded = true },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(0.95f).padding(start = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xB21A2277))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp)) // Add left padding
                Text(
                    selectedCategory,
                    color = Color(0xFFFFC107),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = DarkYellow
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color(0xFFB2A429)) // Dropdown background
        ) {
            val categories = listOf("MOTIVATION", "CRAVINGS", "SUCCESS STORIES", "QUIT TIPS", "STARTING THE JOURNEY")
            val coroutineScope = rememberCoroutineScope()

            categories.forEach { category ->
                val isSelected = category == selectedCategory

                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .background(
                            color = if (isSelected) Color(0xFF1C1C66) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        ).padding(start = 5.dp),
                    text = {
                        Text(
                            text = category,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    },
                    onClick = {
                        selectedCategory = category
                        coroutineScope.launch {
                            delay(350)
                            expanded = false
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun BlogPostList() {
    val posts = listOf(
        Triple("NAME", "10/xx/20xx", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magn"),
        Triple("NAME", "10/xx/20xx", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magn"),
        Triple("NAME", "10/xx/20xx", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magn")
    )

    LazyColumn {
        items(posts.size) { index ->
            BlogPost(
                name = posts[index].first,
                date = posts[index].second,
                content = posts[index].third,

            )
        }
    }
}

@Composable
fun BlogPost(name: String, date: String, content: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(name, color = DarkYellow, fontWeight = FontWeight.ExtraBold)
                Text(date, color = DarkYellow)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(content, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Divider(
            color = Color.Gray,
            thickness = 0.8.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

