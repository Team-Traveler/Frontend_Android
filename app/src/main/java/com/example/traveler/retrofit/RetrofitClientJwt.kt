package com.example.traveler.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClientJwt {
    // BASE_URL
    private const val BASE_URL = "http://15.164.232.95"

    // Timeout 시간 (20초)
    private const val CONNECT_TIMEOUT_SEC = 20L
    private var retrofit: Retrofit? = null

    /** Retrofit instance get - 없을 경우 생성  */
    fun getInstance(token: String?): Retrofit? {
        if (retrofit == null) {
            // Interceptor 생성 - Http 요청/응답의 모든 정보를 로깅할 수 있도록 설정
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // OkHttpClient 생성 - Interceptor 설정, Timeout 시간 설정
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor(loggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .header("Authorization", token!!)
                        .method(original.method, original.body)
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })

            // GsonBuilder 설정
            val gson = GsonBuilder().setLenient().create()

            // Retrofit 설정
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)) // JSON -> Java 객체
                .addConverterFactory(ScalarsConverterFactory.create()) // String, HTML -> Java 객체
                .client(clientBuilder.build())
                .build()
        }
        return retrofit
    }

    /** Retrofit instance 를 통해 RetrofitAPI interface 의 구현체 를 반환  */
    fun getApiService(token: String?): RetrofitAPI {
        return getInstance(token)!!.create(RetrofitAPI::class.java)
    }
}