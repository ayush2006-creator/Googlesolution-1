package com.example.sample_front_screen

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KtorClient.initialize(this)
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




