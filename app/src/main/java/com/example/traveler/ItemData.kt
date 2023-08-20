package com.example.traveler

import android.net.Uri

data class ItemData(
    val impact: String,
    val nameDate: String,
    val hashTags: String,
    var selectedImageUri: Uri?
)
