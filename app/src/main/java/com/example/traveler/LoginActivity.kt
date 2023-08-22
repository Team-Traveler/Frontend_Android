package com.example.traveler

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private val AUTH_URL = "https://kauth.kakao.com/oauth/authorize?" +
            "client_id=efd9cfb14e761f93429607b7f73e2377&" +
            "redirect_uri=http://15.164.232.95:9000/api/auth/kakaoLogin&" +
            "response_type=code"
    private val BASE_URL = "http://15.164.232.95:9000"
    private lateinit var sharedPrefs: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val buttonLogin = findViewById<View>(R.id.button_login)
        val webView = findViewById<WebView>(R.id.webView)

        buttonLogin.setOnClickListener {
            // Show the WebView and load the Kakao login page
            webView.visibility = View.VISIBLE
            webView.settings.javaScriptEnabled = true // Enable JavaScript for OAuth redirection
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    // Handle redirects within WebView
                    if (url != null && url.startsWith("http://15.164.232.95:9000/api/auth/kakaoLogin")) {
                        // Extract the authorization code from the URL
                        val uri = Uri.parse(url)
                        val code = uri.getQueryParameter("code")
                        if (code != null) {
                            sendCodeToServer(code)
                            startActivity(Intent(this@LoginActivity, NaviActivity::class.java))
                        }
                        return true // Return true to indicate the URL has been handled
                    }
                    return false // Let WebView handle other URLs
                }
            }
            webView.loadUrl(AUTH_URL)
        }
    }

    // 웹뷰에서 카카오 로그인 후 리다이렉트되면 호출됨
    override fun onResume() {
        super.onResume()

        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith("http://15.164.232.95:9000/api/auth/kakaoLogin")) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                sendCodeToServer(code)
                startActivity(Intent(this@LoginActivity, NaviActivity::class.java))
            }
        }
    }
    private fun sendCodeToServer(code: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(RetrofitAPI::class.java)
        val call = apiService.sendKakaoCode(code)

        call.enqueue(object : Callback<KakaoLoginResponse> {
            override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
                // 서버 응답 처리
                val TAG = "hyein"
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val editor = sharedPrefs.edit()
                        editor.putString("access_token", responseBody.get_accessToken())
                        editor.apply()
                        Log.i(TAG, "api 요청 성공.\nAccessToken : "+responseBody.get_accessToken())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d(TAG, "카카오 로그인 실패 1")
                    Log.d(TAG, "Error Body: $errorBody")
                    Log.d(TAG, "Response Message: ${response.message()}")
                    Log.d(TAG, "authorization Code: $code")
                }
            }

            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
                val TAG = "hyein"
                Log.d(TAG, "onFailure : " + t.message)

            }
        })
    }
}