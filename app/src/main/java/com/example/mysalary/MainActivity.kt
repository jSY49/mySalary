package com.example.mysalary

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysalary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var salaryViewModel: saveSalaryViewModel
    private lateinit var calsalaryViewModel: calSalaryViewModel

    private val decimalFormat = DecimalFormat("#,###")
    private var result: String = ""

    private val mTimer = Timer()

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
    private fun setUi() {
        val getDate = getDate()
        val context = this

        var sal =0.0f
        var date = 0


        binding.tempDate.text = getDate.date_temp()   //현재 날짜 가져오기

        CoroutineScope(Dispatchers.Main).launch {

            salaryViewModel.getSalry(context).collect {
                if (it == "0") {
                    binding.tempSalary.text = "0"
                } else {
                    salaryViewModel.getSalry(context).collect {
                        Log.d("sp.getSalary",it)
                        sal=it.replace(",", "").toFloat() / calsalaryViewModel.totalday
                        binding.mySalary.text = it
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            salaryViewModel.getPayday(context).collect {
                date=it.toInt()
            }
        }


        mTimer.scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                //누적 금액 갱신
                calsalaryViewModel.cal(sal,date)

            }
        }, 0, 1000)

        refresh()
//            binding.mySalary.text=salaryViewModel.getSalry(context)


    }


    //edit 버튼 클릭
    @RequiresApi(Build.VERSION_CODES.O)
    fun editSalary(view: View) {
        val dlg = salrayEditDialog(this)
        dlg.setOnOKClickedListener { content ->
            binding.mySalary.text = content
//            calsalaryViewModel.getAccu(this)
//            calsalaryViewModel.getAccu(this)
          refresh()
        }
        dlg.show(binding.mySalary.text.toString())
    }

    fun refresh(){
        calsalaryViewModel.res.observe(this, Observer {
            result = decimalFormat.format(it.toString().replace(",", "").toFloat())
            binding.tempSalary.text=result
        })
    }


    private var backPress :Long =0
    override fun onBackPressed() {
        if(System.currentTimeMillis()-backPress<2000){
            mTimer.cancel()
            finish()
            return
        }

        Toast.makeText(this,"뒤로 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
        backPress = System.currentTimeMillis()


    }

    private var conceal =true
    fun concealBtn(view: View) {

        if(!conceal){
            binding.mySalary.visibility=View.GONE
            binding.concealButton.text=resources.getText(R.string.notconceal)
            conceal=true
        }else{
            binding.mySalary.visibility=View.VISIBLE
            binding.concealButton.text=resources.getText(R.string.conceal)
            conceal=false
        }
    }

}