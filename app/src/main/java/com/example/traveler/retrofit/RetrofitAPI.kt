package com.example.traveler.retrofit


import com.example.traveler.KakaoLoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


abstract interface RetrofitAPI {
    @GET("https://kauth.kakao.com/oauth/authorize?client_id=efd9cfb14e761f93429607b7f73e2377&redirect_uri=http://15.164.232.95:9000/api/auth/kakaoLogin&response_type=code")
    fun kakaoLogin(): Call<KakaoLoginResponse>

    @GET("/api/auth/kakaoLogin")
    fun sendKakaoCode(@Query("code") code: String): Call<KakaoLoginResponse>

}