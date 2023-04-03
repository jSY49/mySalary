package com.example.mysalary

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class calSalaryViewModel : ViewModel() {

    var month = 1
    var totalday = 1
    var res = MutableLiveData<String>()


    init {
        month = getDate().month_temp().toInt()
        val year =getDate().year_temp().toInt()
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> {totalday=31}
            2 -> {
                if(year%4==0){  //끝자리 두자리가 4배수 무조건 윤년
                    if(year%100==0) { //근데 끝 두자리00이면 평년
                        if(year%400==0) //근제 끝 두자리 바로 윗자리가 4배수이면 윤년
                            totalday = 29
                        else
                            totalday = 28
                    }
                    else
                        totalday=29
                }else
                    totalday=28
            }
            else -> {totalday=30}
        }
    }

    fun getAccu(context: Context){
        CoroutineScope(Dispatchers.Default).launch{

            val sp = sharedPrefer(context)
            val day = getDate().day_temp().toInt()
//        val day = 25
            val payday=sp.getPayday().toInt()
            val sal_day=sp.getSalry().replace(",","").toFloat()/totalday //하루 버는 월급
            val sal_hour=sal_day/24
            val sal_min=sal_hour/60
            val sal_sec=sal_min/60


            var accu=0.00f
            var accutime=0  //지나간 초

            //날짜 계산(1일 86400초)
            if(payday>day){
                accutime=(totalday-(payday-day))*86400
            }else if(payday<day){
                accutime=(day-payday)*86400
            }else{
                accutime=0
            }

            accutime+=getDate().time_temp_sec() //현재 지나간 총 초
            Log.d("calSal","accutime_sec is $accutime")

            accu=sal_sec*accutime

            res.postValue(accu.toString())
        }

    }


}