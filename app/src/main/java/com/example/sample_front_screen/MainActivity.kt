package com.example.sample_front_screen

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sample_front_screen.sessionmaintainance.SessionManager

class RecoveryApplication : Application() {

    lateinit var sessionManager: SessionManager
        private set

    override fun onCreate() {
        super.onCreate()

        // Initialize KtorClient
        KtorClient.initialize(this)

        // Initialize SessionManager
        sessionManager = SessionManager.getInstance(this)
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Navigation()
            }
        }
    }




