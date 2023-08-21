package com.example.traveler

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ChecklistData(
    val result: List<ChecklistItem>
)

data class ChecklistItem(
    @SerializedName("c_id")
    val cId: Int,
    val title: String,
    val tId: Int,
    val items: List<ChecklistSubItem>
)

data class ChecklistSubItem(
    @SerializedName("id")
    val id: Int,
    val name: String,
    val isChecked: Boolean
)

val json = """{
  "result": [
    {
      "cId": 1,
      "title": "2023년 5월 여행 체크리스트",
      "tId": 1,
      "items": [
        {
          "id": 1,
          "name": "충전기",
          "isChecked": true
        },
        {
          "id": 2,
          "name": "일용품",
          "isChecked": false
        },
        {
          "id": 3,
          "name": "의류",
          "isChecked": false
        }
      ]
    },
    {
      "cId": 2,
      "title": "새로운 체크리스트",
      "tId": 2,
      "items": [
        {
          "id": 1,
          "name": "",
          "isChecked": false
        }
      ]
    }
  ]
}"""

val checklist: ChecklistData = Gson().fromJson(json, ChecklistData::class.java)





