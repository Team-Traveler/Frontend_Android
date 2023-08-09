package com.example.traveler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.traveler.data.OuterDatabase
import com.example.traveler.model.OuterDto
import com.example.traveler.repository.OuterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OuterViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<OuterDto>>
    private val repository : OuterRepository

    init{
        val outerDao = OuterDatabase.getDatabase(application)!!.outerDao()
        repository = OuterRepository(outerDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addOuter(outer : OuterDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOuter(outer)
        }
    }

    fun updateOuter(outer : OuterDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateOuter(outer)
        }
    }

    fun deleteOuter(outer : OuterDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOuter(outer)
        }
    }
}