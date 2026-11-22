package com.bimabaraja.sellora.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.bimabaraja.sellora.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sellora_prefs")

class TokenStore(private val context: Context) {

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val KEY_USER_ID = intPreferencesKey("user_id")
        private val KEY_USER_NAMA = stringPreferencesKey("user_nama")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_ROLE = stringPreferencesKey("user_role")
    }

    val accessTokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_ACCESS_TOKEN]
    }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_REFRESH_TOKEN]
    }

    val userFlow: Flow<UserDto?> = context.dataStore.data.map { prefs ->
        val id = prefs[KEY_USER_ID]
        val nama = prefs[KEY_USER_NAMA]
        val email = prefs[KEY_USER_EMAIL]
        val role = prefs[KEY_USER_ROLE]
        if (id != null && nama != null && email != null && role != null) {
            UserDto(id, nama, email, role)
        } else null
    }

    suspend fun saveAuthData(accessToken: String, refreshToken: String, user: UserDto) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = accessToken
            prefs[KEY_REFRESH_TOKEN] = refreshToken
            prefs[KEY_USER_ID] = user.id
            prefs[KEY_USER_NAMA] = user.nama
            prefs[KEY_USER_EMAIL] = user.email
            prefs[KEY_USER_ROLE] = user.role
        }
    }

    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = accessToken
            prefs[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun clearAuthData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    fun getAccessTokenSync(): String? = runBlocking {
        context.dataStore.data.map { it[KEY_ACCESS_TOKEN] }.first()
    }

    fun getRefreshTokenSync(): String? = runBlocking {
        context.dataStore.data.map { it[KEY_REFRESH_TOKEN] }.first()
    }
}
