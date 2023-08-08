package com.example.traveler.data

import androidx.room.*
import com.example.traveler.model.OuterDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OuterDao {
    // OnConflictStrategy.IGNORE = 동일한 아이디가 있을 시 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOuter(outer : OuterDto)

    @Update
    suspend fun updateOuter(outer : OuterDto)

    @Delete
    suspend fun deleteOuter(outer : OuterDto)

    @Query("SELECT * FROM OuterDto ORDER BY id ASC")
    fun readAllData() : Flow<List<OuterDto>>
}