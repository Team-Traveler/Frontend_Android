package com.example.traveler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.traveler.data.CostDatabase
import com.example.traveler.model.CostDto
import com.example.traveler.repository.CostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 뷰모델은 DB에 직접 접근하지 않아야함. Repository 에서 데이터 통신.
class CostViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<CostDto>>
    private val repository : CostRepository

    init{
        val costDao = CostDatabase.getDatabase(application)!!.costDao()
        repository = CostRepository(costDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addCost(cost : CostDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCost(cost)
        }
    }

    fun updateCost(cost : CostDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCost(cost)
        }
    }

    fun deleteCost(cost : CostDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCost(cost)
        }
    }
}