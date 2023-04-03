package com.example.mysalary

import android.content.Context
import android.content.SharedPreferences

class sharedPrefer(val context: Context) {

    fun setSalry(salary: String,mypayday :String){
        val auto : SharedPreferences = context.getSharedPreferences("UserSalary", Context.MODE_PRIVATE)
        val userSalary = auto.edit()
        userSalary.putString("salary", salary)
        userSalary.putString("payday", mypayday)
        userSalary.apply()

    }

    fun getSharedPreference() : Boolean{
        val auto : SharedPreferences = context.getSharedPreferences("UserSalary", Context.MODE_PRIVATE)
        return !auto.getString("salary", null).isNullOrBlank()
    }

    fun getSalry(): String{
        val auto : SharedPreferences = context.getSharedPreferences("UserSalary", Context.MODE_PRIVATE)
        return auto.getString("salary","0").toString()
    }

    fun getPayday(): String{
        val auto : SharedPreferences = context.getSharedPreferences("UserSalary", Context.MODE_PRIVATE)
        return auto.getString("payday","0").toString()
    }
}