package com.example.traveler.repository

import com.example.traveler.data.OuterDao
import com.example.traveler.model.OuterDto
import kotlinx.coroutines.flow.Flow

class OuterRepository(private val outerDao: OuterDao) {
    val readAllData : Flow<List<OuterDto>> = outerDao.readAllData()

    suspend fun addOuter(outer: OuterDto){
        outerDao.addOuter(outer)
    }

    suspend fun updateOuter(outer: OuterDto){
        outerDao.updateOuter(outer)
    }

    suspend fun deleteOuter(outer: OuterDto){
        outerDao.deleteOuter(outer)
    }
}