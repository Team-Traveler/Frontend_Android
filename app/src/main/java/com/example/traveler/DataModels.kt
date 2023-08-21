package com.example.traveler

data class KakaoLoginResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val grantType: String?,
    val expireIn: Int?
){
    fun get_accessToken(): String? {
        return accessToken
    }
    fun get_refreshToken(): String? {
        return refreshToken
    }
    fun get_grantType(): String? {
        return grantType
    }
    fun get_expireIn(): Int? {
        return expireIn
    }
}


//data class KakaoLoginRequest(
//
//    @SerializedName("accessToken") val accessToken: String,
//    @SerializedName("refreshToken") val refreshToken: String,
//    @SerializedName("grantType") val grantType: String,
//    @SerializedName("expireIn") val expireIn: Int
//) {
//    fun get_AccessToken(): String? {
//        return accessToken
//    }
//    fun get_RefreshToken(): String? {
//        return refreshToken
//    }
//    fun get_GrantType(): String? {
//        return grantType
//    }
//    fun getExpireIn(): Int? {
//        return expireIn
//    }
//}