package com.qasimnawaz019.data.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.qasimnawaz019.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.onBoardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "news_do_compose_pref")

class DataStoreRepositoryImpl(context: Context) : DataStoreRepository {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val isRememberedKey = booleanPreferencesKey(name = "is_remembered")
        val userKey = stringPreferencesKey(name = "user_object_key")
        val tokenKey = stringPreferencesKey(name = "access_token_key")
    }

    private val onBoardingDataStore = context.onBoardingDataStore
    private val dataStore = context.dataStore


    override suspend fun saveOnBoardingState(completed: Boolean) {
        onBoardingDataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return onBoardingDataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
            onBoardingState
        }
    }

    override suspend fun saveRemember(remember: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.isRememberedKey] = remember
        }
    }

    override fun readRememberState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKey.isRememberedKey] ?: false
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.tokenKey] = token
        }
    }

    override fun readAccessToken(): Flow<String?> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKey.tokenKey]
        }
    }

    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.userKey] = Gson().toJson(user).toString()
        }
    }

    override fun getUser(): Flow<User?> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val userJson = preferences[PreferencesKey.userKey]
            Gson().fromJson(userJson, User::class.java)
        }
    }

}