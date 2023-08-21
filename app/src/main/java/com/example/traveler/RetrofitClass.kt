package com.example.traveler

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://15.164.232.95:9000/checklist/{tId}")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _api = retrofit.create(ExampleInterface::class.java)
//    val api
//        get() = api
}