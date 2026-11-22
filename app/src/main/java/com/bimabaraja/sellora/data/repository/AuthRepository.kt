package com.bimabaraja.sellora.data.repository

import com.bimabaraja.sellora.data.local.TokenStore
import com.bimabaraja.sellora.data.remote.ApiService
import com.bimabaraja.sellora.data.remote.dto.*
import com.bimabaraja.sellora.util.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService,
    private val tokenStore: TokenStore
) {
    val currentUserFlow: Flow<UserDto?> = tokenStore.userFlow
    val accessTokenFlow: Flow<String?> = tokenStore.accessTokenFlow

    suspend fun login(email: String, pass: String): Resource<AuthDataDto> {
        return try {
            val response = apiService.login(LoginRequest(email, pass))
            if (response.isSuccessful) {
                val apiRes = response.body()
                val data = apiRes?.data
                if (apiRes?.success == true && data != null) {
                    tokenStore.saveAuthData(data.accessToken, data.refreshToken, data.user)
                    Resource.Success(data)
                } else {
                    Resource.Error(apiRes?.message ?: "Gagal masuk")
                }
            } else {
                val errorMessage = parseErrorMessage(response)
                Resource.Error(errorMessage, response.code())
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server")
        }
    }

    suspend fun register(nama: String, email: String, pass: String): Resource<UserDto> {
        return try {
            val response = apiService.register(RegisterRequest(nama, email, pass))
            if (response.isSuccessful) {
                val apiRes = response.body()
                val data = apiRes?.data
                if (apiRes?.success == true && data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error(apiRes?.message ?: "Gagal mendaftar")
                }
            } else {
                val errorMessage = parseErrorMessage(response)
                Resource.Error(errorMessage, response.code())
            }
        } catch (e: Exception) {
            Resource.Error("Tidak dapat terhubung ke server")
        }
    }

    suspend fun logout(): Resource<Unit> {
        val refreshToken = tokenStore.getRefreshTokenSync()
        try {
            if (!refreshToken.isNullOrEmpty()) {
                apiService.logout(LogoutRequest(refreshToken))
            }
        } catch (e: Exception) {
            // Abaikan error jaringan saat logout, tetap bersihkan data lokal
        } finally {
            tokenStore.clearAuthData()
        }
        return Resource.Success(Unit)
    }

    private fun <T> parseErrorMessage(response: Response<T>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val type = object : TypeToken<ApiResponse<Nothing>>() {}.type
                val errorRes: ApiResponse<Nothing>? = Gson().fromJson(errorBody, type)
                errorRes?.message ?: defaultErrorForCode(response.code())
            } else {
                defaultErrorForCode(response.code())
            }
        } catch (e: Exception) {
            defaultErrorForCode(response.code())
        }
    }

    private fun defaultErrorForCode(code: Int): String {
        return when (code) {
            401 -> "Email atau password salah"
            403 -> "Anda tidak memiliki akses"
            404 -> "Layanan tidak ditemukan"
            500 -> "Terjadi kesalahan pada server"
            else -> "Terjadi kesalahan ($code)"
        }
    }
}
