package com.example.traveler

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleInterface {
    @GET("checklist")
    fun getChecklist(@Query("school_id") schoolId: Int,
                   @Query("grade") grade: Int,
                   @Query("classroom") classroom: Int): Call<ChecklistData>
}