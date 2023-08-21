package com.example.traveler

import android.content.ClipData
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ApiClient {
    private val client = OkHttpClient()
    fun updateNickname(newNickname: String, token: String, callback: (Boolean, String?) -> Unit) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = JSONObject().apply {
            put("nickname", newNickname)
        }.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("http://15.164.232.95:9000/users/nickname")
            .patch(requestBody)
            .addHeader("Authorization", token)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, "API request failed")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback(false, e.message)
            }
        })
    }
}
