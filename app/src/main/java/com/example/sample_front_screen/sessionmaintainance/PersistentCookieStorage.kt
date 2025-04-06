package com.example.sample_front_screen.sessionmaintainance

import android.content.Context
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.http.isSecure
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class SerializableCookie(
    val name: String,
    val value: String,
    val domain: String?,
    val path: String?,
    val expires: Long?,
    val secure: Boolean,
    val httpOnly: Boolean
)

// Extension function for GMTDate conversion
fun GMTDate.toEpochMilliseconds(): Long {
    return this.timestamp * 1000L
}

class PersistentCookieStorage(private val context: Context) : CookiesStorage {
    private val mutex = Mutex()
    private val sharedPrefs = context.getSharedPreferences("KtorCookies", Context.MODE_PRIVATE)
    private val json = Json { encodeDefaults = true }

    private fun Cookie.toSerializable(): SerializableCookie {
        return SerializableCookie(
            name = name,
            value = value,
            domain = domain,
            path = path,
            expires = expires?.toEpochMilliseconds(),
            secure = secure,
            httpOnly = httpOnly
        )
    }

    private fun SerializableCookie.toKtorCookie(): Cookie {
        return Cookie(
            name = name,
            value = value,
            domain = domain,
            path = path,
            expires = expires?.let { GMTDate(it) },
            secure = secure,
            httpOnly = httpOnly,
            extensions = emptyMap()
        )
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        mutex.withLock {
            val cookies = loadAllCookies().toMutableList()

            // Fix domain if null
            val fixedCookie = if (cookie.domain.isNullOrEmpty()) {
                cookie.copy(domain = requestUrl.host)
            } else {
                cookie
            }

            // Remove existing cookies with same name, domain, and path
            cookies.removeAll { existing ->
                existing.name == fixedCookie.name &&
                        existing.domain == fixedCookie.domain &&
                        existing.path == fixedCookie.path
            }

            // Add new cookie
            cookies.add(fixedCookie)

            // Save back to storage
            saveAllCookies(cookies)

            println("Persisted Cookie: ${fixedCookie.name}=${fixedCookie.value}")
        }
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        return mutex.withLock {
            loadAllCookies().filter { cookie ->
                val domainMatches = cookie.domain == requestUrl.host ||
                        (cookie.domain?.let { requestUrl.host.endsWith(".$it") } ?: false)
                val pathMatches = requestUrl.encodedPath.startsWith(cookie.path ?: "/")
                val secureMatches = !cookie.secure || requestUrl.protocol.isSecure()
                val notExpired = cookie.expires == null || cookie.expires!!.timestamp * 1000L > System.currentTimeMillis()

                domainMatches && pathMatches && secureMatches && notExpired
            }.onEach { cookie ->
                println("Loaded Cookie: ${cookie.name}=${cookie.value}")
            }
        }
    }

    private fun loadAllCookies(): List<Cookie> {
        val jsonString = sharedPrefs.getString("cookies", "[]") ?: "[]"
        return try {
            json.decodeFromString<List<SerializableCookie>>(jsonString)
                .map { it.toKtorCookie() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveAllCookies(cookies: List<Cookie>) {
        val serializableCookies = cookies.map { it.toSerializable() }
        sharedPrefs.edit()
            .putString("cookies", json.encodeToString(serializableCookies))
            .apply()
    }

    override fun close() {
        // Optional: Clear cookies if needed
        // sharedPrefs.edit().remove("cookies").apply()
    }

    fun clearCookies() {
        sharedPrefs.edit().remove("cookies").apply()
    }
}