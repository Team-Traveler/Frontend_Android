package com.example.traveler

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.databinding.ActivityMakeplanBinding

class MakeplanActivity: AppCompatActivity() {
    lateinit var binding: ActivityMakeplanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeplanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //취소 누르면 다시 이전 화면인 RECOMMEND로 넘어가기
        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, RecommendFragment::class.java)
            startActivity(intent)
        }

        //7초 후 추천 완료 화면으로 자동으로 넘어감
        Handler().postDelayed({
            val completedIntent = Intent(this, CompletedplanActivity::class.java)
            startActivity(completedIntent)
            finish()
        }, 7000)
    }
}