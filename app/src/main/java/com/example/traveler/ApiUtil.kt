package com.example.traveler

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object ApiUtil {
    val client = OkHttpClient()

    fun sendDeleteRequest(tid: Int, onComplete: () -> Unit) {
        val request = Request.Builder()
            .url("http://15.164.232.95:9000/travel/$tid")
            .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImV4cCI6MTY5MjY3ODEwMX0.LIZXQcGqTuSrgOr7wDJznhsmVkitbhMNitx8bdLkV6cQE5_7fic9wpskhHg9UK5ZcUfZ1LRk9Cl5wAfZ4itjlw")
            .delete()
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    Log.d("Network", "Delete Request Success")
                    onComplete.invoke()
                } else {
                    Log.e("Network", "Delete Request Failed with response code: ${response.code}")
                }
            }
        })
    }





}
