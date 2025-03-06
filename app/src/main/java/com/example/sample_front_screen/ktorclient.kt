package com.example.sample_front_screen

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object KtorClient {
    private const val BASE_URL = "https://recoveryapp.onrender.com"

    val client = HttpClient(OkHttp) {
        // Set the base URL for all requests
        defaultRequest {
            url(BASE_URL)
        }

        // Cookie handling
        install(HttpCookies) {
            storage = object : CookiesStorage {
                private val cookieStore = mutableListOf<io.ktor.http.Cookie>()
                private val mutex = Mutex()

                override suspend fun addCookie(requestUrl: Url, cookie: io.ktor.http.Cookie) {
                    mutex.withLock {
                        // Fix: Set the domain if it's null
                        val fixedCookie = if (cookie.domain.isNullOrEmpty()) {
                            cookie.copy(domain = requestUrl.host)
                        } else {
                            cookie
                        }

                        // Remove existing cookies with the same name, domain, and path
                        cookieStore.removeAll { existingCookie ->
                            existingCookie.name == fixedCookie.name &&
                                    existingCookie.domain == fixedCookie.domain &&
                                    existingCookie.path == fixedCookie.path
                        }

                        // Add the new cookie
                        cookieStore.add(fixedCookie)

                        // Log received cookies for debugging
                        println("Received Cookie: ${fixedCookie.name}=${fixedCookie.value}, Domain: ${fixedCookie.domain}, Path: ${fixedCookie.path}, Secure: ${fixedCookie.secure}")
                    }
                }

                override suspend fun get(requestUrl: Url): List<io.ktor.http.Cookie> {
                    return mutex.withLock {
                        // Filter cookies that match the request URL
                        cookieStore.filter { cookie ->
                            // Check if the cookie is valid for the current domain and path
                            val domainMatches =
                                cookie.domain == requestUrl.host || requestUrl.host.endsWith("." + cookie.domain)
                            val pathMatches = requestUrl.encodedPath.startsWith(cookie.path ?: "/")
                            val secureMatches = !cookie.secure || requestUrl.protocol.isSecure()

                            domainMatches && pathMatches && secureMatches
                        }.also { validCookies ->
                            // Log cookies being sent
                            validCookies.forEach { cookie ->
                                println("Sending Cookie: ${cookie.name}=${cookie.value}, Domain: ${cookie.domain}, Path: ${cookie.path}, Secure: ${cookie.secure}")
                            }
                        }
                    }
                }

                override fun close() {
                    runBlocking {
                        mutex.withLock {
                            cookieStore.clear()
                            println("CookiesStorage closed: All cookies cleared.")
                        }
                    }
                }
            }
        }

        // Add Content Negotiation for JSON
        install(ContentNegotiation) {
            gson()
        }
    }

    // API service implementations
    val authApi = AuthApiImpl(client)
    val streakApi = StreakApiServiceImpl(client)
    val leaderboardApi = LeaderboardApiImpl(client)
    val blogApi=BlogsApiImpl(client)
}