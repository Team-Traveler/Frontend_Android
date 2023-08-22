package com.example.traveler

import com.google.gson.annotations.SerializedName

data class ItemListData (
    @SerializedName("name"      ) var name      : String?  = null,
    @SerializedName("isChecked" ) var isChecked : Boolean? = null,
    @SerializedName("cId"       ) var cId       : Int?     = null
)