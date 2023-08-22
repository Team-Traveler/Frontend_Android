package com.example.traveler


data class Spot(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val sid: Int
)

data class Course(
    val dcId: Int,
    val spot1: Spot?,
    // 다른 spot 정보도 필요하다면 여기에 추가
    val numOfDay: Int
)

data class TravelItem(
    val title: String,
    val destination: String,
    val start_date: String,
    val end_date: String,
    val tid: Int
    // 다른 필요한 정보도 추가
)

data class ApiResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<Contents>
)
