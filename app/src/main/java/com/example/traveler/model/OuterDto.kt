package com.example.traveler.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OuterDto(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val title : String
)
