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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sample_front_screen.breathingexcersize.breathingexcersize
import com.example.sample_front_screen.breathingexcersize.breathscreen1
import com.example.sample_front_screen.breathingexcersize.breathscreen2
import com.example.sample_front_screen.breathingexcersize.breathscreen4
import com.example.sample_front_screen.breathingexcersize.breathscreen5
import com.example.sample_front_screen.inputs.EnemyChoose
import com.example.sample_front_screen.inputs.Expenditure
import com.example.sample_front_screen.inputs.TriggerPoints
import com.example.sample_front_screen.reusablefunctions.pretaskscreen
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
    val context = LocalContext.current
    

    NavHost(navController = navController, startDestination = Screens.screen2.route) {

        composable(route = Screens.screen2.route) {
            ScreenImage(R.drawable.screen2, navController, 1500L, Screens.screen4.route)
        }

        composable(route = Screens.screen4.route) {
            ScreenImage(R.drawable.screen4, navController, 1500L, Screens.screen7.route)
        }

        composable(route = Screens.screen7.route) {
            ScreenImage(R.drawable.screen7, navController, 1500L, Screens.welcome.route)
        }
        composable(route=Screens.welcome.route){
            pretaskscreen(
                taskhead = "Hey Warrior!",
                taskd = " We are here to support you  in\n your QUIT JOURNEY.\n We Believe in You.",
                buttonm = "YES LET'S DO IT",
                taskmessage = "Are you ready for your \n first TASK?",

                navController = navController,
                nextScreen = Screens.task1.route
            )

        }
        composable(route=Screens.task1.route){
            pretaskscreen(buttonm = "LET'S BEGIN", taskhead = "Tap & Smash", subheading = "TIME FOR YOUR FIRST CHALLENGE", title = "SMASH & WIN", navController = navController, taskd ="the feelings your addiction\n brings—fear, anxiety, guilt,\ncravings, and more. Every\ntap is a step toward \nfreedom.",nextScreen = Screens.addictionstart.route )
        }
        composable(route=Screens.addictionstart.route){
            AddictedScreenStart(context=context ,navController= navController)
        }
        composable(route=Screens.addiction.route){
            PreviewWordGrid(
                context = context,
                navController = navController
            )
        }

        composable(route=Screens.signin.route){
            SignIn(AuthViewModel(
                authApi =  AuthApiImpl(KtorClient.client)
            ),navController)

        }
        composable(route=Screens.signup.route){
            SignUp(navController, AuthViewModel(authApi =  AuthApiImpl(KtorClient.client)))
        }
        composable(route=Screens.enemychose.route){
            EnemyChoose(navController=navController)
        }
        composable(Screens.expenditure.route){
            Expenditure(navController=navController)
        }
        composable(Screens.triggers.route){
            TriggerPoints(navController=navController)
        }
        composable(Screens.breathscreen1.route){
            breathscreen1(navController=navController)
        }
        composable(Screens.breathscreen2.route){
            breathscreen2(navController)

        }
        composable(Screens.breathscreen3.route){
            breathingexcersize(navController)
        }
        composable(Screens.breathscreen4.route){
            breathscreen4(navController)
        }
        composable(Screens.breathscreen5.route){
            breathscreen5(navController)
        }
        composable(Screens.lighttower.route){

        }
        

    }
}

