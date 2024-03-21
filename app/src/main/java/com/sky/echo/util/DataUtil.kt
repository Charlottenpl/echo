package com.sky.echo.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object DataUtil {
    fun readStr(context: Context, key: String): String {
        val EXAMPLE_COUNTER = stringPreferencesKey("example_counter")
        val exampleCounterFlow: Flow<String> = context.dataStore.data.map {
            it ->
            it[EXAMPLE_COUNTER] ?: ""
        }

    }
}