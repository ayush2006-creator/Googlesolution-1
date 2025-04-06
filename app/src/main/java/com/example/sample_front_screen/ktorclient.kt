package com.example.sample_front_screen

import android.content.Context
import com.example.sample_front_screen.sessionmaintainance.PersistentCookieStorage
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.gson.*
import java.util.concurrent.atomic.AtomicBoolean

object KtorClient {
    private const val BASE_URL = "https://recoveryapp.onrender.com"
    private lateinit var persistentCookieStorage: PersistentCookieStorage
    private val isInitialized = AtomicBoolean(false)

    /**
     * Must be called before accessing any KtorClient properties
     */
    fun initialize(context: Context) {
        if (!isInitialized.getAndSet(true)) {
            persistentCookieStorage = PersistentCookieStorage(context.applicationContext)
        }
    }

    val client: HttpClient by lazy {
        check(isInitialized.get()) { "KtorClient must be initialized first with Context" }

        HttpClient(OkHttp) {
            // Set the base URL for all requests
            defaultRequest {
                url(BASE_URL)
            }

            // Persistent Cookie handling
            install(HttpCookies) {
                storage = persistentCookieStorage
            }

            // Add Content Negotiation for JSON
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                    disableHtmlEscaping()
                }
            }

            // Add default request headers
            install(DefaultRequest) {
                header(HttpHeaders.Accept, "application/json")
                header(HttpHeaders.ContentType, "application/json")
            }

            // Enable logging for debugging
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            // Configure timeouts
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
        }
    }

    // API service implementations (lazy to ensure client is initialized)
    val authApi by lazy { AuthApiImpl(client) }
    val streakApi by lazy { StreakApiServiceImpl(client) }
    val leaderboardApi by lazy { LeaderboardApiImpl(client) }
    val blogApi by lazy { BlogsApiImpl(client) }

    /**
     * Clears all persisted cookies (for logout)
     */
    fun clearCookies() {
        check(isInitialized.get()) { "KtorClient must be initialized first" }
        persistentCookieStorage.clearCookies()
    }

    /**
     * Checks if the client has been initialized
     */
    fun isInitialized(): Boolean = isInitialized.get()
}