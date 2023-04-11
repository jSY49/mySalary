package com.example.mysalary

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "UserSalary")
class sharedPrefer(val context: Context) {

    private val salaryKey = stringPreferencesKey("salary") // string 저장 키값
    private val paydayKey = stringPreferencesKey("payday") // string 저장 키값

    suspend fun setSalry(salary: String, mypayday: String) {

        context.dataStore.edit { preferences ->
            preferences[salaryKey] = salary
            preferences[paydayKey] = mypayday
        }

    }

    fun getSalry(): Flow<String> {

        val text: Flow<String> = context.dataStore.data
            .catch { exception ->       //가져오는 작업이 실패했을 때
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[salaryKey] ?: "0"
            }

        return  text
    }

    fun getPayday(): Flow<String> {
        val text: Flow<String> = context.dataStore.data
            .catch { exception ->       //가져오는 작업이 실패했을 때
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[paydayKey] ?: "0"
            }

        return text
    }

}