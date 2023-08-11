package com.example.traveler

data class Contents(
    var name:String,        //코스명
    var day:String,         //몇박몇일
    var date:String,        // ~ string값으로 (수정필요)
    var location:String,    //위치명
    var start: String,  //츌발
    var end: String,    //도착
//위도경도 추가필요

    )