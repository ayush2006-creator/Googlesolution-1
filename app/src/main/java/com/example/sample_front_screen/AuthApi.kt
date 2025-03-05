package com.example.sample_front_screen

import com.example.sample_front_screen.leaderboardmain.GlobalLeaderboard

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @POST("api/signup")
    @Headers("Accept: application/json")

    fun signup(@Body userData: Signup): Call<SignupResponse>

    @POST("api/login")
    @Headers("Accept: application/json")
    fun login(@Body credentials: Login): Call<LoginResponse>
}
interface StreakApiService {
    @POST("api/streaks/checkin")
    @Headers("Accept: application/json")
    fun streak():Call<CheckinResponse>

    @GET("api/streaks/data ")
    @Headers("Accept: application/json")
    fun getStreakData() : Call<CheckinResponse>

}
interface LeaderboardApi{
    @GET("api/leaderboard/global/current")
    @Headers("Accept: application/json")
    fun globalleaderboard():Call<List<GlobalLeaderboard>>


}