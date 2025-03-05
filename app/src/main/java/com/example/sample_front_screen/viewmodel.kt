package com.example.sample_front_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authApi: AuthApi) : ViewModel() {

    private val _signupState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signupState: StateFlow<AuthState> = _signupState.asStateFlow()

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    fun signup(email: String, password: String, username: String) {
        viewModelScope.launch {
            try {
                _signupState.value = AuthState.Loading
                Log.d("Signup", "Starting signup request...")

                val response = authApi.signup(Signup(username, email, password)).execute()

                Log.d("Signup", "Response Code: ${response.code()}")
                Log.d("Signup", "Response Message: ${response.message()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Signup", "Response Body: $responseBody")

                    _signupState.value = AuthState.Success(responseBody!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Signup", "Error Body: $errorBody")

                    _signupState.value = AuthState.Failure(response.code(), errorBody)
                }
            } catch (e: Exception) {
                Log.e("Signup", "Exception: ${e.message}")
                _signupState.value = AuthState.Failure(-1, e.message)
            }
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = AuthState.Loading
                val response = authApi.login(Login(email, password)).execute()
                if (response.isSuccessful) {
                    _loginState.value = AuthState.Success(response.body()!!)
                } else {
                    _loginState.value = AuthState.Failure(response.code())
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Failure(-1, e.message)
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val data: Any) : AuthState()
    data class Failure(val code: Int, val message: String? = null) : AuthState()
}

