package com.example.traveler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_login = findViewById<ImageButton>(R.id.button_login)

        button_login.setOnClickListener {
//            startActivity(Intent(this, NaviActivity::class.java))
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error != null) {
                    Log.e("test", "로그인 실패", error)
                    Toast.makeText(this,"로그인 실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    Log.i("test", "로그인 성공 ${token.accessToken}")
                    Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NaviActivity::class.java)
                    startActivity(intent)
//                    startActivity(Intent(this, NaviActivity::class.java))
                    // 카카오 로그인 성공 처리
                    // 사용자 정보 요청
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            // 사용자 정보 요청 실패 처리
                        } else if (user != null) {
                            // 사용자 정보 요청 성공 처리
                            val userId = user.id
                            val nickname = user.kakaoAccount?.profile?.nickname
                            val profileImageUrl = user.kakaoAccount?.profile?.profileImageUrl
                        }
                    }
                }
            }
        }
    }
}