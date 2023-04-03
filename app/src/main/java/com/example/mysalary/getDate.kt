package com.example.mysalary

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class getDate {

    fun date_temp(): String{
        val localDate: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
        return localDate.format(formatter)
    }
    fun day_temp() :String{
        val localDate: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd")
        return localDate.format(formatter)
    }

    fun month_temp() :String{
        val localDate: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM")
        return localDate.format(formatter)
    }

    fun year_temp() :String{
        val localDate: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy")
        return localDate.format(formatter)
    }

    fun time_temp_sec() :Int{
        val localTime :LocalTime = LocalTime.now()
        val hour = localTime.hour
        val min = localTime.minute
        val sec = localTime.second

        return hour*3600+min*60+sec
    }

}