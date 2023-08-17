package com.example.traveler

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "b6f86cf07bf7eb06fba747dc3761bc2a")
    }
}