@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sample_front_screen
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sample_front_screen.fontfunctions.HeadingText
import com.example.sample_front_screen.fontfunctions.SubHeadingText

import com.example.sample_front_screen.ui.theme.DarkYellow
import com.example.sample_front_screen.ui.theme.Yellow


@Composable
fun SignIn(viewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) } // To show error messages

    val loginState by viewModel.loginState.collectAsState()

    // Handle navigation based on login state
    LaunchedEffect(loginState) {
        Log.d("LaunchedEffect", "Current loginState: $loginState")
        when (loginState) {
            is AuthState.Success -> {
                Log.d("LaunchedEffect", "Navigating to screen4")
                navController.navigate(Screens.screen4.route) {
                    popUpTo(Screens.signin.route) { inclusive = true }
                }
                viewModel.resetLoginState()
            }
            is AuthState.Failure -> {
                Log.d("LaunchedEffect", "Login failed: ${(loginState as AuthState.Failure).message}")
                errorMessage = (loginState as AuthState.Failure).message ?: "Login failed. Please try again."
            }
            else -> {
                Log.d("LaunchedEffect", "Other state: $loginState")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.letsdoit),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {
        Column(
            modifier = Modifier.padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AssisstantTalkShort(text = "So happy to see you again")
            HeadingText("Welcome Back to\n " + "        Recovery")

            SubHeadingText("It’s time to pick up where you left off.\n" +
                    "  The road has been waiting for you.")

            // Show error message if login fails
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            TextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, start = 16.dp, end = 16.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                label = { Text("Enter Email") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Yellow,
                    unfocusedContainerColor = Color(0x66FFEB3B),
                )
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter Password") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, start = 16.dp, end = 16.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Yellow,
                    unfocusedContainerColor = Color(0x66FFEB3B),
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Gray
                )
            )

            Button(
                onClick = { viewModel.login(email, password)
                    errorMessage = null},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 18.dp, start = 16.dp, end = 16.dp),
                colors = ButtonColors(
                    contentColor = Color.Black,
                    containerColor = Yellow,
                    disabledContainerColor = Yellow,
                    disabledContentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    focusedElevation = 10.dp
                )
            ) {
                Text(
                    text = "SIGN IN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.lato)))
            }

            Row(modifier = Modifier.padding(top = 10.dp)) {
                Text("Don't have an account?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.lato)),
                    color = DarkYellow)
                Text("  SIGN UP",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.lato)),
                    color = Yellow,
                    modifier = Modifier.clickable(onClick = {navController.navigate(Screens.signup.route)})
                )
            }
        }
    }
}





@Composable
fun AssisstantTalkSide(text: String,modifier: Modifier){
    Box(modifier= modifier){
        ConstraintLayout() {
            val (image) = createRefs()
            val (message) = createRefs()
            Box(
                modifier = Modifier.constrainAs(message) {

                    top.linkTo(parent.top, margin = 20.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
                    .fillMaxWidth() // Ensures alignment works properly
                    .heightIn(min = 50.dp) // Optional min height
            ) {
                Card(
                    modifier = Modifier
                        .width(220.dp) // Fixed width

                        .wrapContentWidth()// Expands upwards
                        .align(Alignment.BottomEnd) // Aligns to the bottom
                        .padding(end = 16.dp)
                        .shadow(
                            elevation = 30.dp),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 16.dp
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 20.dp,


                        ),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFCCCCCC)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = text,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.assistant), // Replace with your image
                contentDescription = null,

                modifier = Modifier.size(220.dp).constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)

                }

            )
        }}}


@Composable
fun AssisstantTalkShort(text:String,modifier: Modifier=Modifier){
    Box(modifier=modifier){
    ConstraintLayout {
        val (image) = createRefs()
        val (message) = createRefs()
        Box(
            modifier = Modifier.constrainAs(message) {

                top.linkTo(parent.top)
                start.linkTo(parent.start, margin =37.dp)
            }
                .fillMaxWidth() // Ensures alignment works properly
                .heightIn(min = 50.dp) // Optional min height
        ) {
            Card(
                modifier = Modifier
                    .width(180.dp) // Fixed width
                    .wrapContentHeight()
                    .wrapContentWidth()// Expands upwards
                    .align(Alignment.BottomEnd) // Aligns to the bottom
                    .padding(end = 16.dp)
                    .shadow(
                        elevation = 30.dp),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 16.dp
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp,

                    
                ),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFCCCCCC)
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = text,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.assistant), // Replace with your image
            contentDescription = null,

            modifier = Modifier.size(200.dp).constrainAs(image) {
                top.linkTo(message.bottom, margin = (-40).dp)
                centerHorizontallyTo(parent)

            }

        )
    }}}