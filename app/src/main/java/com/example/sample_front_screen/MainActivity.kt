package com.example.sample_front_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sample_front_screen.RetrofitClient.retrofit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authApi: AuthApi = retrofit.create(AuthApi::class.java)
        setContent {

            SignUp(AuthViewModel(authApi))
            }
        }
    }




