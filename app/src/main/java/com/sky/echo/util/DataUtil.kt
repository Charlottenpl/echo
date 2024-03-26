package com.sky.echo.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sms.manager.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * 封装了DataStore的使用，提供了简化的API来存储和读取键值对数据。
 * 这个工具类专门用于处理字符串类型的数据，但可以根据需要扩展以支持其他数据类型。
 */
@SuppressLint("StaticFieldLeak")
object DataUtil {

    private lateinit var context: Context


    fun init(con: Context){
        context = con
    }

    /**
     * 将一个字符串类型的值存储到DataStore中。
     *
     * @param key 要存储的键。
     * @param value 要存储的值。
     */
    suspend fun putString( key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    /**
     * 从DataStore中异步获取一个字符串类型的值。
     * 如果指定的键不存在，则返回默认值。
     *
     * @param key 要读取的键。
     * @param defaultValue 如果键不存在时返回的默认值。
     * @return 一个包含读取值的字符串，或者默认值。
     */
    suspend fun getString(key: String, defaultValue: String = ""): String {
        val dataKey = stringPreferencesKey(key)
        return context.dataStore.data.first()[dataKey] ?: defaultValue
    }

    /**
     * 从DataStore中获取一个字符串类型的值的Flow。
     * 这个Flow可以被收集，以便在数据变化时得到更新。
     *
     * @param key 要读取的键。
     * @param defaultValue 如果键不存在时返回的默认值。
     * @return 一个Flow，包含读取值的字符串，或者默认值。
     */
    suspend fun getStringFlow(key: String, defaultValue: String = ""): String {
        return context.dataStore.data.catch { exception->
            if (exception is IOException) {
                Logger.e("Error reading from datastore $exception")
            }
        }.map {
            it[stringPreferencesKey(key)] ?: defaultValue
        }.first()
    }
}

val data_userId = stringPreferencesKey("resolution")
val data_password = stringPreferencesKey("theme")