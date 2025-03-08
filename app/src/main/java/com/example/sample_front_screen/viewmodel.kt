package com.example.sample_front_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.HttpRequestTimeoutException
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

                val response = authApi.signup(Signup(username, email, password))
                Log.d("Signup", "Response: $response")
                _signupState.value = AuthState.Success(response)
                if (response.message == null ||
                    response.message == "Success" ||
                    response.message == "Login successful" ||
                    response.message.contains("success") ||
                    response.message.contains("welcome")) {
                    // This indicates a successful login
                    _loginState.value = AuthState.Success(response)
                } else {
                    // Any other message is an error
                    Log.d("Login", "Login failed with message: ${response.message}")
                    _loginState.value = AuthState.Failure(401, response.message)
                }
            } catch (e: ClientRequestException) {
                Log.e("Signup", "Client Error: ${e.response.status.value} ${e.message}")
                _signupState.value = AuthState.Failure(e.response.status.value, e.message)
            } catch (e: ServerResponseException) {
                Log.e("Signup", "Server Error: ${e.response.status.value} ${e.message}")
                _signupState.value = AuthState.Failure(e.response.status.value, e.message)
            } catch (e: HttpRequestTimeoutException) {
                Log.e("Signup", "Request Timeout: ${e.message}")
                _signupState.value = AuthState.Failure(-1, "Request timed out. Please try again.")
            } catch (e: Exception) {
                Log.e("Signup", "Exception: ${e.message}")
                _signupState.value = AuthState.Failure(-1, e.message)
            }
        }
    }
    fun resetLoginState() {
        _loginState.value = AuthState.Idle
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = AuthState.Loading
                Log.d("Login", "Starting login request...")

                val response = authApi.login(Login(email, password))
                Log.d("Login", "Response: $response")

                // Check if the response is actually a success
                if (response.message == null ||
                    response.message == "Success" ||
                    response.message == "Login successful" ||
                    response.message.contains("success") ||
                    response.message.contains("welcome")) {
                    // This indicates a successful login
                    _loginState.value = AuthState.Success(response)
                } else {
                    // Any other message is an error
                    Log.d("Login", "Login failed with message: ${response.message}")
                    _loginState.value = AuthState.Failure(401, response.message)
                }
            } catch (e: Exception) {
                // Handle exceptions as before
                Log.e("Login", "Exception: ${e.message}")
                _loginState.value = AuthState.Failure(-1, e.message)
            }
        }
    }}
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val data: Any) : AuthState()
    data class Failure(val code: Int, val message: String? = null) : AuthState()
}
