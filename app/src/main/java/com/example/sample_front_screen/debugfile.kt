package com.example.sample_front_screen



fun main()  {

    val credentials = Login("jkjdh@gmail.com","jnasjkd")

    // Signup

    // Login
    val signup =Signup("JSK1","jkjd1h@gmail.com","jnasjkd")
    val signupResponse=RetrofitClient.authApi.login(credentials).execute()
    if (signupResponse.isSuccessful) {
        println("Login successful: ${signupResponse.headers()}")
    } else {
        println("Login failed: ${signupResponse.message()}")

    }




    val leaderboard = RetrofitClient.leaderboardApi.globalleaderboard().execute()
    if (leaderboard.isSuccessful) {
        println(leaderboard.body())
    } else {
        println(" failed: ${leaderboard.code()}")

    }


}
