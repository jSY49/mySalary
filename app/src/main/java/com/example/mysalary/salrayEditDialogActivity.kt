package com.example.mysalary

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mysalary.databinding.ActivitySalrayEditDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class salrayEditDialog(private val context: AppCompatActivity) {

    val binding by lazy { ActivitySalrayEditDialogBinding.inflate(context.layoutInflater) }
    private val dlg = Dialog(context)
    private lateinit var listener : MyDialogOKClickedListener

    private val decimalFormat = DecimalFormat("#,###")
    private var result: String = ""


    private lateinit var salaryViewModel: saveSalaryViewModel
    fun show(salary : String) {

        salaryViewModel = ViewModelProvider(context)[saveSalaryViewModel::class.java]

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 클릭시 종료x

        binding.salaryText.addTextChangedListener(watcher)
        binding.salaryText.setText(salary) //전달 받은 텍스트
        CoroutineScope(Dispatchers.Main).launch {
            salaryViewModel.getPayday(context).collect{
                binding.paydayText.setText(it)
            }
        }

//        binding.paydayText.setText(salaryViewModel.getPayday(context))

        //ok 버튼 동작
        binding.saveButton.setOnClickListener {
            val mySalary= binding.salaryText.text.toString()
            val mypayday= binding.paydayText.text.toString()
            if(mypayday.toInt()<32){
                listener.onOKClicked(mySalary)
                CoroutineScope(Dispatchers.Default).launch {
                    salaryViewModel.setSalry(mySalary,mypayday,context)
                }
                dlg.dismiss()
            }else{
               binding.paydayText.backgroundTintList=ContextCompat.getColorStateList(context,R.color.red)
            }

        }

        //cancel 버튼 동작
        binding.cancelButton.setOnClickListener {

            dlg.dismiss()
        }
        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }

    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                result = decimalFormat.format(charSequence.toString().replace(",","").toDouble())
                binding.salaryText.setText(result);
                binding.salaryText.setSelection(result.length);
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }
}