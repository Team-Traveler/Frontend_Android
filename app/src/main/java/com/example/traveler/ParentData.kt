package com.example.traveler

/*data class ParentData (
    val date:String,//몇일차 1일차
    val day:String,//날짜 12.10~ 12.11
    val childDataList: List<ChildData> //childData 담을 그릇 (데이터 일정) ->course연결

    )*/

data class ParentData(val date: String,val day: String,  val childDataList: List<ChildData>) {
    constructor(day: String, date: String) : this(day, date, emptyList())
}
