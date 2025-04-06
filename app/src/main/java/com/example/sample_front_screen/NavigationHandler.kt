package com.example.sample_front_screen

import SideMenuLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sample_front_screen.breathingexcersize.BreathingExercise
import com.example.sample_front_screen.breathingexcersize.breathscreen1
import com.example.sample_front_screen.breathingexcersize.breathscreen2
import com.example.sample_front_screen.breathingexcersize.breathscreen4
import com.example.sample_front_screen.breathingexcersize.breathscreen5
import com.example.sample_front_screen.community.NewPostScreen
import com.example.sample_front_screen.community.header
import com.example.sample_front_screen.inputs.EnemyChoose
import com.example.sample_front_screen.inputs.Expenditure
import com.example.sample_front_screen.inputs.TriggerPoints
import com.example.sample_front_screen.lighttower.SplashScreen
import com.example.sample_front_screen.milestone.milestonelist
import com.example.sample_front_screen.reusablefunctions.pretaskscreen
import com.example.sample_front_screen.tasks.MoodSelectorScreen
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
    val application = context.applicationContext as RecoveryApplication
    val sessionManager = application.sessionManager
    val isLoggedIn by sessionManager.isLoggedIn.collectAsState()

    // Determine start destination based on login status
    val startDestination = if (sessionManager.isUserLoggedIn()) {
        Screens.screen4.route // User is logged in, go to main screen

    } else {
        Screens.screen2.route // User is not logged in, go to onboarding
    }


    NavHost(navController = navController, startDestination = startDestination) {

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
                nextScreen = Screens.signin.route
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
                authApi = AuthApiImpl(KtorClient.client),
                sessionManager =application.sessionManager
            ),navController)

        }
        composable(route=Screens.signup.route){
            SignUp(navController, AuthViewModel(
                authApi = AuthApiImpl(KtorClient.client),
                sessionManager = application.sessionManager
            ))
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
            BreathingExercise(navController=navController)
        }
        composable(Screens.breathscreen4.route){
            breathscreen4(navController)
        }
        composable(Screens.breathscreen5.route){
            breathscreen5(navController)
        }
        composable(Screens.lighttower.route){
            SplashScreen(navController,homepage = true)

        }
        composable(Screens.lighttoweronboard.route){
            SplashScreen(navController,homepage = false)
        }
        composable(Screens.dashboard1.route){
            SideMenuLayout(navController= navController)


        }
        composable(Screens.community.route){
            header(navController= navController)
        }
        composable(Screens.new.route){
            NewPostScreen(navController)
        }
        composable(Screens.distractme.route){
            Text("Underprogress")

        }
        composable(Screens.milestones.route){
            milestonelist(navController)
        }

        composable(Screens.myrecoverygarden.route){
            Text("Underprogress")

        }
        composable(Screens.talktome.route){
            Text("Underprogress")
        }
        composable(Screens.maytasks.route){
            MoodSelectorScreen(navController=navController)
        }
        composable(Screens.mygoals.route){
            Text("underprogress")
        }




        

    }
}

