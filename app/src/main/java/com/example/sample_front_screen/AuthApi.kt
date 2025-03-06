package com.example.sample_front_screen

import com.example.sample_front_screen.leaderboardmain.GlobalLeaderboard
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*






interface AuthApi {
    suspend fun signup(userData: Signup): SignupResponse

    suspend fun login(credentials: Login): LoginResponse
}

interface StreakApiService {
    suspend fun streak(): CheckinResponse

    suspend fun getStreakData(): CheckinResponse

}

interface LeaderboardApi {
    suspend fun globalLeaderboard(): List<GlobalLeaderboard>
    suspend fun globalLeaderboardLongest():List<GlobalLeaderboard>
    suspend fun globalLeaderboardFriendCurrent():List<GlobalLeaderboard>

}
interface BlogApi{
    suspend fun blogsCreate():Blogs
    suspend fun blogsGet():Blogs
}


class AuthApiImpl(private val client: HttpClient) : AuthApi {
    override suspend fun signup(userData: Signup): SignupResponse {
        return client.post("api/signup") {
            contentType(ContentType.Application.Json)
            setBody(userData)
        }.body()
    }

    override suspend fun login(credentials: Login): LoginResponse {
        return client.post("api/login") {
            contentType(ContentType.Application.Json)
            setBody(credentials)
        }.body()
    }
}

class StreakApiServiceImpl(private val client: HttpClient) : StreakApiService {
    override suspend fun streak(): CheckinResponse {
        return client.post("api/streaks/checkin") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getStreakData(): CheckinResponse {
        return client.get("api/streaks/data") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}

class LeaderboardApiImpl(private val client: HttpClient) : LeaderboardApi {
    override suspend fun globalLeaderboard():List<GlobalLeaderboard> {
        return client.get("api/leaderboard/global/current") {
            contentType(ContentType.Application.Json)
        }.body()
    }
    override suspend fun globalLeaderboardLongest():List<GlobalLeaderboard> {
        return client.get("api/leaderboard/global/longest") {
            contentType(ContentType.Application.Json)
        }.body()
    }
    override suspend fun globalLeaderboardFriendCurrent():List<GlobalLeaderboard> {
        return client.get("api/leaderboard/friends/current") {
            contentType(ContentType.Application.Json)
        }.body()
    }

}
class BlogsApiImpl(private val client: HttpClient):BlogApi{
    override suspend fun blogsCreate():Blogs{
        return client.post("api/blogs/create"){
            contentType(ContentType.Application.Json)
        }.body()
    }
    override suspend fun blogsGet():Blogs{
        return client.get("api/blogs/all") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}






