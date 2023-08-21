package com.example.traveler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import com.example.traveler.databinding.ActivityEditProfileBinding
import com.example.traveler.databinding.CustomBackBarBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class EditProfile : AppCompatActivity() {
    val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater)}
    private val apiClient = ApiClient()

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
        binding.editbtn.setOnClickListener {

//            val newNickname = binding.username.text.toString()
//
//            val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjg5Njk1NTE0fQ.mXTd2f1NwwTKygOxknRJTp-NnAinpE_w1IHAnGTDya-aWQuQDXT_E0a8i1NP4Qd8vRrkmdD9Nie41Mx4ruLb1w" // 본인의 인증 토큰으로 대체해야 합니다.

//            apiClient.updateNickname(newNickname, token) { isSuccess, errorMessage ->
//                if (isSuccess) {
//                    // 닉네임 수정 성공 처리
//                    val resultIntent = Intent()
//                    resultIntent.putExtra("name", newNickname)
//                    setResult(Activity.RESULT_OK, resultIntent)
//                    finish()
//                } else {
//                    // 에러 처리
//                    if (errorMessage != null) {
//                        Log.e("EditProfile", errorMessage)
//                    }
//                }
//            }

            val modifiedValue = username.text.toString()

            // 메인 화면으로 수정 결과값 전달
            val resultIntent = Intent()
            resultIntent.putExtra("name", modifiedValue)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()


        }
    }
}