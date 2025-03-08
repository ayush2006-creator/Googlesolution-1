package com.example.sample_front_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun ScreenImage(imageResId: Int, navController: NavController, gap: Long, nextScreen: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) { // Ensures no white gaps
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "App Logo",
            contentScale = ContentScale.Crop, // Ensures full-screen cover
            modifier = Modifier.fillMaxSize()
        )
    }

    LaunchedEffect(Unit) {
        delay(gap)
        nextScreen?.let {
            navController.navigate(it) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true } // Clears back stack
                launchSingleTop = true // Prevents duplicate screens
            }

    }
}}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    

    NavHost(navController = navController, startDestination = Screens.screen1.route) {
        composable(route = Screens.screen1.route) {
            ScreenImage(R.drawable.screen1, navController, 1500L, Screens.screen2.route)
        }
        composable(route = Screens.screen2.route) {
            ScreenImage(R.drawable.screen2, navController, 1500L, Screens.screen3.route)
        }
        composable(route = Screens.screen3.route) {
            ScreenImage(R.drawable.screen3, navController, 1500L, Screens.screen4.route)
        }
        composable(route = Screens.screen4.route) {
            ScreenImage(R.drawable.screen4, navController, 1500L, Screens.screen5.route)
        }
        composable(route = Screens.screen5.route) {
            ScreenImage(R.drawable.screen5, navController, 1500L, Screens.screen6.route)
        }
        composable(route = Screens.screen6.route) {
            ScreenImage(R.drawable.screen6, navController, 1500L, Screens.screen7.route)
        }
        composable(route = Screens.screen7.route) {
            ScreenImage(R.drawable.screen7, navController, 1500L, Screens.screen8.route)
        }
        composable(route = Screens.screen8.route) {
            ScreenImage(R.drawable.screen8, navController, 1500L, Screens.signin.route) // No next screen
        }
        composable(route=Screens.signin.route){
            SignIn(AuthViewModel(
                authApi =  AuthApiImpl(KtorClient.client)
            ),navController)

        }
        composable(route=Screens.signup.route){
            SignUp(navController, AuthViewModel(authApi =  AuthApiImpl(KtorClient.client)))
        }
    }
}

