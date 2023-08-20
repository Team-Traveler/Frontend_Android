package com.example.traveler.retrofit


import com.example.traveler.KakaoLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

abstract interface RetrofitAPI {
    @POST("/api/auth/kakao")
    fun kakaoLogin(@Body requestBody: Map<String, String>): Call<KakaoLoginResponse>
}