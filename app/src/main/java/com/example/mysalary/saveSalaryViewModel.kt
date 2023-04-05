package com.example.mysalary

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class saveSalaryViewModel: ViewModel() {

    suspend fun setSalry(salary: String, mypayday :String, context: Context){
        sharedPrefer(context).setSalry(salary, mypayday)
    }

    fun getSalry(context: Context): Flow<String> = sharedPrefer(context).getSalry()

    fun getPayday(context: Context): Flow<String> = sharedPrefer(context).getPayday()
}