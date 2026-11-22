package com.bimabaraja.sellora.data.remote

import com.bimabaraja.sellora.data.local.TokenStore
import com.bimabaraja.sellora.data.remote.dto.ApiResponse
import com.bimabaraja.sellora.data.remote.dto.AuthDataDto
import com.bimabaraja.sellora.data.remote.dto.RefreshTokenRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class TokenAuthenticator(
    private val tokenStore: TokenStore,
    private val baseUrl: String
) : Authenticator {

    private val gson = Gson()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.encodedPath.contains("auth/refresh")) {
            runBlocking { tokenStore.clearAuthData() }
            return null
        }

        synchronized(this) {
            val currentToken = tokenStore.getAccessTokenSync()
            val requestToken = response.request.header("Authorization")?.replace("Bearer ", "")

            if (currentToken != null && currentToken != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            val refreshToken = tokenStore.getRefreshTokenSync()
            if (refreshToken.isNullOrEmpty()) {
                runBlocking { tokenStore.clearAuthData() }
                return null
            }

            val client = OkHttpClient()
            val jsonBody = gson.toJson(RefreshTokenRequest(refreshToken))
            val body = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())

            val refreshRequest = Request.Builder()
                .url("${baseUrl}auth/refresh")
                .post(body)
                .build()

            try {
                val refreshResponse = client.newCall(refreshRequest).execute()
                if (refreshResponse.isSuccessful) {
                    val responseBodyStr = refreshResponse.body?.string()
                    val type = object : TypeToken<ApiResponse<AuthDataDto>>() {}.type
                    val apiResponse: ApiResponse<AuthDataDto>? = gson.fromJson(responseBodyStr, type)

                    val newAuthData = apiResponse?.data
                    if (apiResponse?.success == true && newAuthData != null) {
                        runBlocking {
                            tokenStore.updateTokens(newAuthData.accessToken, newAuthData.refreshToken)
                        }
                        return response.request.newBuilder()
                            .header("Authorization", "Bearer ${newAuthData.accessToken}")
                            .build()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            runBlocking { tokenStore.clearAuthData() }
            return null
        }
    }
}
