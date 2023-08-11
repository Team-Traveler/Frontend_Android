package com.example.traveler.repository

import com.example.traveler.data.InnerDao
import com.example.traveler.model.InnerDto
import kotlinx.coroutines.flow.Flow

// 앱에서 사용하는 데이터와 그 데이터 통신을 하는 역할
class InnerRepository(private val innerDao: InnerDao) {
    val readAllData : Flow<List<InnerDto>> = innerDao.readAllData()

    suspend fun addInner(inner: InnerDto){
        innerDao.addInner(inner)
    }

    suspend fun updateInner(inner: InnerDto){
        innerDao.updateInner(inner)
    }

    suspend fun deleteInner(inner: InnerDto){
        innerDao.deleteInner(inner)
    }
}