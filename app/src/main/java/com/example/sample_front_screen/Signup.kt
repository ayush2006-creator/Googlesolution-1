package com.example.sample_front_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import com.example.sample_front_screen.ui.theme.DarkYellow
import com.example.sample_front_screen.ui.theme.Yellow


@Composable
fun SignUp(navController: NavController,viewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.letsdoit),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )



    }
    val SignupState by viewModel.signupState.collectAsState()
    LaunchedEffect(SignupState) {

        Log.d("LaunchedEffect", "Current SignupState: $SignupState")

        when (SignupState) {
            is AuthState.Success -> {
                Log.d("LaunchedEffect", "Navigating to screen4")
                navController.navigate(Screens.screen4.route) {
                    popUpTo(Screens.signin.route) { inclusive = true }
                }
                viewModel.resetLoginState()
            }
            is AuthState.Failure -> {
                Log.d("LaunchedEffect", "Signup failed: ${(SignupState as AuthState.Failure).message}")
                errorMessage = (SignupState as AuthState.Failure).message ?: "Login failed. Please try again."
            }
            else -> {
                Log.d("LaunchedEffect", "Other state: $SignupState")
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize().padding(top=40.dp)){
        Column(
            modifier = Modifier.padding(30.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AssisstantTalkShort(text = "A road trip awaits you.")
            HeadingText("Welcome Back to\n "+"        Recovery")

            SubHeadingText("It’s time to pick up where you left off.\n" +
                    "  The road has been waiting for you.")
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            TextField(
                value = name,
                onValueChange = { name = it }, // Required to update state
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top =18.dp,start = 16.dp,end=16.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                label = { Text("Enter name") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Yellow,  // Background when focused
                    unfocusedContainerColor = Color(0x66FFEB3B), // Background when not focused


                    focusedLeadingIconColor = Color.Black, // Icon color when focused
                    unfocusedLeadingIconColor = Color.Gray
                )


            )
            TextField(
                value = email,
                onValueChange = { email = it }, // Required to update state
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top =14.dp,start = 16.dp,end=16.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                label = { Text("Enter Email") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Yellow,  // Background when focused
                    unfocusedContainerColor = Color(0x66FFEB3B), // Background when not focused


                    focusedLeadingIconColor = Color.Black, // Icon color when focused
                    unfocusedLeadingIconColor = Color.Gray
                )


            )

            TextField(
                value = password,
                onValueChange = { password = it },

                label = { Text("Enter Password") },
                shape = RoundedCornerShape(10.dp),// ✅ Label behaves like the Email field
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top =14.dp, start = 16.dp,end=16.dp)
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
                    focusedContainerColor = Yellow,  // Background when focused
                    unfocusedContainerColor = Color(0x66FFEB3B)
                    , // Background when not focused
                    // Cursor color
                    // Bottom line when not focused
                    focusedTrailingIconColor = Color.Black, // Trailing icon color when focused
                    unfocusedTrailingIconColor = Color.Gray
                )
            )
            Button(
                onClick = {viewModel.signup(email,password,name)
                    errorMessage = null},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 18.dp,start = 16.dp,end=16.dp)
                ,
                colors = ButtonColors(
                    contentColor = Color.Black, containerColor = Yellow,
                    disabledContainerColor = Yellow,
                    disabledContentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp,   // Normal elevation
                    pressedElevation = 2.dp,   // Elevation when pressed

                    focusedElevation = 10.dp   // When focused

                ) ){
                Text(
                    text = "SIGN UP",
                    fontSize = 18.sp,  // ✅ Correct font size
                    fontWeight = FontWeight.Bold, // Optional for emphasis
                    textAlign = TextAlign.Center, // Ensures text is centered
                    fontFamily = FontFamily(Font(R.font.lato)),

                    )
            }
            Row(modifier = Modifier.padding(top =10.dp)) {


                Text("Already have an account?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.lato)),
                    color = DarkYellow )
                Text("  SIGN IN",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.lato)),
                    color = Yellow,
                    modifier=Modifier.clickable(onClick = {})
                )
            }





        }
    }
}
