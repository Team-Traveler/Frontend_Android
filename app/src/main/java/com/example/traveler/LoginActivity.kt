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
            startActivity(Intent(this, NaviActivity::class.java))
//            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
//                if (error != null) {
//                    Log.e("test", "로그인 실패", error)
//                    Toast.makeText(this,"로그인 실패", Toast.LENGTH_SHORT).show()
//                } else if (token != null) {
//                    Log.i("test", "로그인 성공 ${token.accessToken}")
//                    Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, NaviActivity::class.java)
//                    startActivity(intent)
////                    startActivity(Intent(this, NaviActivity::class.java))
//                    // 카카오 로그인 성공 처리
//                    // 사용자 정보 요청
//                    UserApiClient.instance.me { user, error ->
//                        if (error != null) {
//                            // 사용자 정보 요청 실패 처리
//                        } else if (user != null) {
//                            // 사용자 정보 요청 성공 처리
//                            val userId = user.id
//                            val nickname = user.kakaoAccount?.profile?.nickname
//                            val profileImageUrl = user.kakaoAccount?.profile?.profileImageUrl
//                        }
//                    }
//                }
//                else if (token == null) {
//                    Toast.makeText(this, "토큰 없음", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, NaviActivity::class.java)
//                    startActivity(intent)
//                }
//            }
        }
    }
}

//package com.example.traveler
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.ImageButton
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.traveler.retrofit.RetrofitAPI
//import com.kakao.sdk.auth.model.OAuthToken
//import com.kakao.sdk.user.UserApiClient
//import retrofit2.Call


//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class LoginActivity : AppCompatActivity() {
//    private val BASE_URL = "http://15.164.232.95:9000"
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL) // 카카오 로그인 API의 base URL로 변경해야 함
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
////        val keyHash = Utility.getKeyHash(this)
////        Log.d("Hash", keyHash)
//
//        val button_login = findViewById<ImageButton>(R.id.button_login)
//
//        button_login.setOnClickListener {
//            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
//                login();
//            } else {
//                accountLogin();
//            }
//        }
//    }
//    // 로그인 기능 구현
//    // 카카오 계정이 있는 경우 바로 로그인
//    fun login() {
//        val TAG = "login()"
//        UserApiClient.instance.loginWithKakaoTalk(this) { oAuthToken: OAuthToken?, error: Throwable? ->
//            if (oAuthToken == null) {
//                Log.e(TAG, "토큰 없음")
//            } else if (error != null) {
//                Log.e(TAG, "로그인 실패", error)
//            } else if (oAuthToken != null) {
//                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.accessToken)
//                /** API 요청 실행  */
//                kakaoLoginAPI(oAuthToken.accessToken)
//            }
//            null
//        }
//    }
//    // 없는 경우 직접 계정으로 로그인
//    fun accountLogin() {
//        val TAG = "accountLogin()-KAKAO"
//        UserApiClient.instance.loginWithKakaoAccount(this) { oAuthToken: OAuthToken?, error: Throwable? ->
//            if (oAuthToken == null) {
//                Log.e(TAG, "토큰 없음")
//            } else if (error != null) {
//                Log.e(TAG, "로그인 실패", error)
//            } else if (oAuthToken != null) {
//                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.accessToken)
//                /** API 요청 수행  */
//                kakaoLoginAPI(oAuthToken.accessToken)
//            }
//            null
//        }
//    }
//    private fun kakaoLoginAPI(token: String) {
//        val authorizationCode = token
//        val requestBody = mapOf("authorizationCode" to authorizationCode)
//
//        val apiService = retrofit.create(RetrofitAPI::class.java)
//        val call = apiService.kakaoLogin(requestBody)
//        call.enqueue(object : Callback<KakaoLoginResponse> {
//            override fun onResponse(
//                call: Call<KakaoLoginResponse>,
//                response: Response<KakaoLoginResponse>
//            ) {
//                if (response.isSuccessful) {
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "로그인이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT
//                    ).show()
//                    Log.d("accountLogin()", "로그인이 성공적으로 완료되었습니다.")
//                    startActivity(Intent(this@LoginActivity, NaviActivity::class.java))
//                } else {
//                    val errorBody = response.errorBody()?.string()
//                    Log.d("accountLogin()", "카카오 로그인 실패 1")
//                    Log.d("accountLogin()", "Error Body: $errorBody")
//                    Log.d("accountLogin()", "Response Message: ${response.message()}")
//                    Log.d("accountLogin()", "authorization Code: $requestBody")
//                }
//            }
//            override fun onFailure(call: Call<KakaoLoginResponse?>, t: Throwable) {
//                Log.d("accountLogin()", "onFailure : " + t.message)
//            }
//        })
//    }
//}