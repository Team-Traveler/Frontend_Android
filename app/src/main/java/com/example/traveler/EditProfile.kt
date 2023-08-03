package com.example.traveler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import com.example.traveler.databinding.ActivityEditProfileBinding
import com.example.traveler.databinding.CustomBackBarBinding

class EditProfile : AppCompatActivity() {
    val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

/*        // 두 번째 액티비티에서는 onCreate 시 액션바를 숨깁니다.
        supportActionBar?.hide()

        // 액션바 설정
        binding.toolbarLayout.customActionBar.setOnClickListener {
            onBackPressed()
            supportActionBar?.show()

        }*/

        // 커스텀 액션 바 레이아웃 인플레이트
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_back_bar)
        //

        val username=binding.username
        // 이전 페이지로 돌아가기 위해 [수정버튼] 클릭 이벤트 처리
        binding.button.setOnClickListener {
            val modifiedValue = username.text.toString()

            // 메인 화면으로 수정 결과값 전달
            val resultIntent = Intent()
            resultIntent.putExtra("name", modifiedValue)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()


        }
    }
}