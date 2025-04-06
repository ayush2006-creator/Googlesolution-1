package com.example.sample_front_screen

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    KtorClient.initialize(context)

    // THEN get the client instance
    val authApi = AuthApiImpl(KtorClient.client)
    val streakApi = StreakApiServiceImpl(KtorClient.client)
    val leaderboardApi = LeaderboardApiImpl(KtorClient.client)


    // Test Signup


    // Test Login
    val loginData = Login("john@example.com", "password123")
    try {
        val loginResponse = authApi.login(loginData)
        println("Login Response: $loginResponse")
    } catch (e: Exception) {
        println("Login Failed: ${e.message}")
    }

    // Test Streak Checkin
    try {
        val streakResponse = streakApi.streak()
        println("Streak Checkin Response: $streakResponse")
    } catch (e: Exception) {
        println("Streak Checkin Failed: ${e.message}")
    }

    // Test Get Streak Data
    try {
        val streakDataResponse = streakApi.getStreakData()
        println("Streak Data Response: $streakDataResponse")
    } catch (e: Exception) {
        println("Failed to fetch streak data: ${e.message}")
    }

    // Test Global Leaderboard
    try {
        val leaderboard = leaderboardApi.globalLeaderboard()
        println("Global Leaderboard: $leaderboard")
    } catch (e: Exception) {
        println("Failed to fetch leaderboard: ${e.message}")
    }
}
