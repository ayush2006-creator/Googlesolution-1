package com.example.sample_front_screen.leaderboardmain




data class GlobalLeaderboard(
    val userId: String,

    val email: String,

    val currentStreak: Int? = null,
)

data class FriendsLeaderboard(
    val current: List<UserStreak>,
    val longest: List<UserStreak>
)

data class UserStreak(
    val userId: Int,

    val email: String,

    val currentStreak: Int? = null,

)