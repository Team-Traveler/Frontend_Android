package com.example.traveler

data class Contents(
    var title:String,        //코스명 -
    //var day:String="",         //몇박몇일
    //var date:String="",        // ~ string값으로 (수정필요)
    var destination:String,    //위치명 -
    var start_date: String,  //츌발  -
    var end_date: String,    //도착  -
    var write_status:Int = 0,  //추천유무,직접(0) 추천(1)
    //timestatus : 예정(0) 지난(1)
    var tid:Int=11,


    /*    var created_at:String="",
        var time_status:Int=0,
        var courses: MutableList<String> = mutableListOf(),
        var tid:Int=0,
        var uid: Int=0*/
)
//위도경도 추가필요

//기존 변수명
//    var name:String,        //코스명 -
//    var day:String="",         //몇박몇일
//    var date:String="",        // ~ string값으로 (수정필요)
//    var location:String,    //위치명 -
//    var start: String,  //츌발  -
//    var end: String,    //도착  -
//    var write_status:Int = 0
