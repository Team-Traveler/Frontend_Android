package com.example.traveler.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InnerDto(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var check: Boolean,
    var title: String
)