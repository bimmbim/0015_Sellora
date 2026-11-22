package com.bimabaraja.sellora.data.remote

import com.bimabaraja.sellora.data.local.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val path = originalRequest.url.encodedPath

        if (path.contains("auth/login") || path.contains("auth/register") || path.contains("auth/refresh")) {
            return chain.proceed(originalRequest)
        }

        val token = tokenStore.getAccessTokenSync()
        return if (!token.isNullOrEmpty()) {
            val authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}
