package com.example.traveler.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/** Retrofit 객체 생성 클래스  */
object RetrofitClient {
    // BASE_URL
    private const val BASE_URL = "http://15.164.232.95:9000"

    // Timeout 시간 (20초)
    private const val CONNECT_TIMEOUT_SEC = 20L
    private var retrofit: Retrofit? = null
    val instance: Retrofit?
        /** Retrofit instance get - 없을 경우 생성  */
        get() {
            if (retrofit == null) {
                // Interceptor 생성 - Http 요청/응답의 모든 정보를 로깅할 수 있도록 설정
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                // OkHttpClient 생성 - Interceptor 설정, Timeout 시간 설정
                val clientBuilder = OkHttpClient.Builder()
                clientBuilder.addInterceptor(loggingInterceptor)
                    .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)

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
    val getApiService: RetrofitAPI
        /** Retrofit instance 를 통해 RetrofitAPI interface 의 구현체 를 반환  */
        get() = instance!!.create(RetrofitAPI::class.java)
}