package com.example.traveler.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CostDto(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val category: String,
    val content: String,
    val cost: String
)