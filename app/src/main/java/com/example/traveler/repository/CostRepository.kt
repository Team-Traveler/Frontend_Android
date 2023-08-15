package com.example.traveler.repository

import com.example.traveler.data.CostDao
import com.example.traveler.model.CostDto
import kotlinx.coroutines.flow.Flow

// 앱에서 사용하는 데이터와 그 데이터 통신을 하는 역할
class CostRepository(private val costDao: CostDao) {
    val readAllData : Flow<List<CostDto>> = costDao.readAllData()

    suspend fun addCost(cost: CostDto){
        costDao.addCost(cost)
    }
    suspend fun updateCost(cost: CostDto){
        costDao.updateCost(cost)
    }
    suspend fun deleteCost(cost: CostDto){
        costDao.deleteCost(cost)
    }
}