package com.example.mysalary

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel

class saveSalaryViewModel: ViewModel() {

    fun setSalry(salary: String,mypayday :String,context: Context){
        sharedPrefer(context).setSalry(salary, mypayday)
    }

    fun getSalry(context: Context): String= sharedPrefer(context).getSalry()

    fun getPayday(context: Context): String = sharedPrefer(context).getPayday()
}