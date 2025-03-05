package com.example.sample_front_screen
import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private const val BASE_URL = "https://recoveryapp.onrender.com"



    private val client = OkHttpClient.Builder()
        .cookieJar(object : CookieJar {
            private val cookieStore = mutableListOf<Cookie>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                // Clear existing cookies for the same domains/paths
                cookieStore.removeAll { existingCookie ->
                    cookies.any { newCookie ->
                        existingCookie.domain == newCookie.domain &&
                                existingCookie.path == newCookie.path
                    }
                }

                // Add new cookies
                cookieStore.addAll(cookies)

                // Optional: Log received cookies for debugging
                cookies.forEach { cookie ->
                    println("Received Cookie: ${cookie.name}=${cookie.value}, Domain: ${cookie.domain}, Path: ${cookie.path}")
                }
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                // Filter cookies that match the request URL
                val validCookies = cookieStore.filter { cookie ->
                    // Check if cookie is valid for the current domain and path
                    (cookie.domain == url.host || url.host.endsWith("." + cookie.domain)) &&
                            url.encodedPath.startsWith(cookie.path ?: "/")
                }

                // Optional: Log cookies being sent
                validCookies.forEach { cookie ->
                    println("Sending Cookie: ${cookie.name}=${cookie.value}")
                }

                return validCookies
            }
        })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)

        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val streakApi: StreakApiService = retrofit.create(StreakApiService::class.java)
    val leaderboardApi:LeaderboardApi= retrofit.create(LeaderboardApi::class.java)



}