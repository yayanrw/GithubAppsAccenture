package com.heyproject.githubapps.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
Written by Yayan Rahmat Wijaya on 11/13/2022 23:13
Github : https://github.com/yayanrw
 **/

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingDataStoreImpl @Inject constructor(
    private val context: Context
) : SettingDataStore {
    private val THEME_KEY = booleanPreferencesKey("theme_settings")

    override fun getThemeSetting(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }
}

interface SettingDataStore {
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}