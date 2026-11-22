package com.bimabaraja.sellora

import android.content.Context
import com.bimabaraja.sellora.data.local.TokenStore
import com.bimabaraja.sellora.data.remote.AuthInterceptor
import com.bimabaraja.sellora.data.remote.ApiService
import com.bimabaraja.sellora.data.remote.TokenAuthenticator
import com.bimabaraja.sellora.data.repository.AuthRepository
import com.bimabaraja.sellora.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val tokenStore: TokenStore
    val apiService: ApiService
    val authRepository: AuthRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    override val tokenStore: TokenStore by lazy {
        TokenStore(context)
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val authInterceptor by lazy {
        AuthInterceptor(tokenStore)
    }

    private val tokenAuthenticator by lazy {
        TokenAuthenticator(tokenStore, Constants.BASE_URL)
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepository(apiService, tokenStore)
    }
}
