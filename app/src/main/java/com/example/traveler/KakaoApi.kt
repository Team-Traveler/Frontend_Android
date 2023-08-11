/*
package com.example.traveler

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/local/search/keyword.json")


    // Single을 사용하여 장소 검색을 수행
    fun searchPlaces(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Single<PlaceResponse>



    //ffe3d8c113f35596913222a53d09aaea
}

data class PlaceResponse(
    val documents: List<PlaceDocument>
)

data class PlaceDocument(
    val place_name: String,
    val x: Double,
    val y: Double
)

*/
