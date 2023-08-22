package com.example.traveler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.databinding.ActivityCompletedplanBinding

class CompletedplanActivity: AppCompatActivity() {
    lateinit var binding: ActivityCompletedplanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletedplanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //btnComplete 클릭시 나의 여행 부분으로 넘어가기
    }
}