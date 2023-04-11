package com.example.mysalary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.mysalary.databinding.ActivitySplashBinding


class splashActivity : AppCompatActivity() {

    val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageAnim =AnimationUtils.loadAnimation(this,R.anim.splash_anim)
        binding.image.startAnimation(imageAnim)

        moveMain(3)	//1초 후 main activity 로 넘어감
    }

    private fun moveMain(sec: Int) {
        Handler().postDelayed(Runnable {
            //new Intent(현재 context, 이동할 activity)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent) //intent 에 명시된 액티비티로 이동
            finish() //현재 액티비티 종료
        }, (1000 * sec).toLong()) // sec초 정도 딜레이를 준 후 시작
    }

}