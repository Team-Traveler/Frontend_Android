package com.example.traveler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.kakao.sdk.user.UserApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class LoginActivity : AppCompatActivity() {
    private val BASE_URL = "http://15.164.232.95:9000"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // 카카오 로그인 API의 base URL로 변경해야 함
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        val button_login = findViewById<ImageButton>(R.id.button_login)

        button_login.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                login();
            } else {
                accountLogin();
            }
        }
    }
    // 로그인 기능 구현
    // 카카오 계정이 있는 경우 바로 로그인
    fun login() {
        val TAG = "login()"
        UserApiClient.instance.loginWithKakaoTalk(this) { oAuthToken: OAuthToken?, error: Throwable? ->
            if (oAuthToken == null) {
                Log.e(TAG, "토큰 없음")
            } else if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.accessToken)
            }
        }
    }
    // 없는 경우 직접 계정으로 로그인
    fun accountLogin() {
        val TAG = "accountLogin()-KAKAO"
        UserApiClient.instance.loginWithKakaoAccount(this) { oAuthToken: OAuthToken?, error: Throwable? ->
            if (oAuthToken == null) {
                Log.e(TAG, "토큰 없음")
            } else if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.accessToken)
                startActivity(Intent(this@LoginActivity, NaviActivity::class.java))
            }
        }
    }

}
