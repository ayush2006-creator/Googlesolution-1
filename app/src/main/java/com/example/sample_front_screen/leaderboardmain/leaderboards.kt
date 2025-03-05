package com.example.sample_front_screen.leaderboardmain




data class GlobalLeaderboard(
    val current: List<UserStreak>,
    val longest: List<UserStreak>
)

data class FriendsLeaderboard(
    val current: List<UserStreak>,
    val longest: List<UserStreak>
)

data class UserStreak(
    val userId: Int,
    val name: String,
    val email: String,
    val profilePic: String,
    val currentStreak: Int? = null,
    val longestStreak: Int? = null
)