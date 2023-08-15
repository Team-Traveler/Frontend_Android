package com.example.traveler.model

import com.example.traveler.model.CostDto

data class DayDto (
    val date: String,
    val day: String,
    val costDataList: List<CostDto>
)