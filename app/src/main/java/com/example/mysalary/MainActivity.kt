package com.example.mysalary

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysalary.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var salaryViewModel: saveSalaryViewModel
    private lateinit var calsalaryViewModel: calSalaryViewModel

    private val decimalFormat = DecimalFormat("#,###")
    private var result: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        salaryViewModel = ViewModelProvider(this)[saveSalaryViewModel::class.java]
        calsalaryViewModel = ViewModelProvider(this)[calSalaryViewModel::class.java]

        setUi()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUi(){
        val getDate = getDate()
        val context = this

        binding.tempDate.text=getDate.date_temp()   //현재 날짜 가져오기
        if(sharedPrefer(this).getSharedPreference()){
            binding.mySalary.text=salaryViewModel.getSalry(context)
            val timer = Timer()
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    // 누적 금액 갱신
                    calsalaryViewModel.getAccu(context)
                }
            }
            timer.schedule(timerTask, 0, 10000)
            calsalaryViewModel.res.observe(context, Observer {

                result = decimalFormat.format(it.toString().replace(",","").toFloat())
                binding.tempSalary.setText(result);


            })

        }else{
            binding.mySalary.text="0"
            binding.tempSalary.text="0"
        }
    }

    //edit 버튼 클릭
    @RequiresApi(Build.VERSION_CODES.O)
    fun editSalary(view: View) {

        val dlg = salrayEditDialog(this)
        dlg.setOnOKClickedListener{ content ->
            binding.mySalary.text = content
            calsalaryViewModel.getAccu(this)
            calsalaryViewModel.getAccu(this)
            calsalaryViewModel.res.observe(this, Observer {
                binding.tempSalary.text=it
            })
        }
        dlg.show(binding.mySalary.text.toString())
    }

}