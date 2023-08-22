package com.example.traveler.viewmodel

import androidx.lifecycle.ViewModel
import com.example.traveler.model.DayDto

class DayViewModel : ViewModel() {

    // dayDataList를 ViewModel 내에서 관리합니다.
    private val dayDataList = ArrayList<DayDto>()

    // 선택한 날짜에 해당하는 DayDto 객체를 가져오는 함수
    fun getDayDataForDate(selectedDate: String): DayDto? {
        return dayDataList.find { it.date == selectedDate }
    }
}