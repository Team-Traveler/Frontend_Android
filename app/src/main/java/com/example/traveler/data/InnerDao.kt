package com.example.traveler.data

import androidx.room.*
import com.example.traveler.model.InnerDto
import kotlinx.coroutines.flow.Flow

@Dao
interface InnerDao {
    // OnConflictStrategy.IGNORE = 동일한 아이디가 있을 시 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInner(inner : InnerDto)

    @Update
    suspend fun updateInner(inner : InnerDto)

    @Delete
    suspend fun deleteInner(inner : InnerDto)

    @Query("SELECT * FROM InnerDto ORDER BY id ASC")
    fun readAllData() : Flow<List<InnerDto>>
}