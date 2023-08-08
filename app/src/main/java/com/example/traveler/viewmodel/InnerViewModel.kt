package com.example.traveler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.traveler.data.InnerDatabase
import com.example.traveler.model.InnerDto
import com.example.traveler.repository.InnerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 뷰모델은 DB에 직접 접근하지 않아야함. Repository 에서 데이터 통신.
class InnerViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<InnerDto>>
    private val repository : InnerRepository

    init{
        val innerDao = InnerDatabase.getDatabase(application)!!.innerDao()
        repository = InnerRepository(innerDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addInner(inner : InnerDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addInner(inner)
        }
    }

    fun updateInner(inner : InnerDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateInner(inner)
        }
    }

    fun deleteInner(inner : InnerDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteInner(inner)
        }
    }
}