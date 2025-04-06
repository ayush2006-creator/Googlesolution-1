package com.example.sample_front_screen.sessionmaintainance

import android.content.Context
import android.content.SharedPreferences
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.http.parseServerSetCookieHeader
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.text.SimpleDateFormat
import java.util.*

class PersistentCookieStorage(context: Context) : CookiesStorage {
    private val mutex = Mutex()
    private val cookies = mutableMapOf<String, MutableMap<String, Cookie>>()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_cookies", Context.MODE_PRIVATE)

    private val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    init {
        // Load cookies from SharedPreferences
        sharedPreferences.all.forEach { (key, rawCookieString) ->
            if (rawCookieString is String) {
                try {
                    parseServerSetCookieHeader(rawCookieString)?.let { cookie ->
                        val domain = cookie.domain ?: ""
                        val name = cookie.name
                        if (domain.isNotEmpty() && name.isNotEmpty()) {
                            val hostCookies = cookies.getOrPut(domain) { mutableMapOf() }
                            hostCookies[name] = cookie
                        }
                    }
                } catch (e: Exception) {
                    // Remove invalid cookie
                    sharedPreferences.edit().remove(key).apply()
                }
            }
        }
    }

    override suspend fun get(requestUrl: Url): List<Cookie> = mutex.withLock {
        val now = GMTDate()
        return cookies.values.flatMap { it.values }
            .filter { cookie ->
                // Check if cookie is valid for the request URL and not expired
                (cookie.expires?.let { it > now } ?: true) &&
                        ((cookie.domain?.let { requestUrl.host.endsWith(it) } ?: false) ||
                                cookie.domain == "localhost")
            }
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) = mutex.withLock {
        val domain = cookie.domain ?: requestUrl.host
        val name = cookie.name

        if (domain.isNotEmpty() && name.isNotEmpty()) {
            val hostCookies = cookies.getOrPut(domain) { mutableMapOf() }
            val cookieWithDomain = if (cookie.domain == null || cookie.domain!!.isEmpty()) {
                cookie.copy(domain = domain)
            } else {
                cookie
            }

            hostCookies[name] = cookieWithDomain

            // Save to SharedPreferences
            sharedPreferences.edit().putString(
                "${domain}_${name}",
                renderSetCookieHeader(cookieWithDomain)
            ).apply()
        }
    }

    override fun close() {
        // Nothing to close
    }

    fun clearCookies() {
        cookies.clear()
        sharedPreferences.edit().clear().apply()
    }

    private fun renderSetCookieHeader(cookie: Cookie): String {
        val result = StringBuilder()
        result.append("${cookie.name}=${cookie.value}")

        if (cookie.encoding != null) {
            result.append("; Encoding=${cookie.encoding}")
        }

        if (cookie.maxAge != null) {
            result.append("; Max-Age=${cookie.maxAge}")
        }

        if (cookie.expires != null) {
            result.append("; Expires=${dateFormat.format(Date(cookie.expires!!.timestamp))}")
        }

        cookie.domain?.let { result.append("; Domain=$it") }
        cookie.path?.let { result.append("; Path=$it") }

        if (cookie.secure) {
            result.append("; Secure")
        }

        if (cookie.httpOnly) {
            result.append("; HttpOnly")
        }

        return result.toString()
    }
}