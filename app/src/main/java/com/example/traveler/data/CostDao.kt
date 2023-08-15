package com.example.traveler.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.traveler.model.CostDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CostDao {
    // OnConflictStrategy.IGNORE = 동일한 아이디가 있을 시 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCost(cost: CostDto)

    @Update
    suspend fun updateCost(cost: CostDto)

    @Delete
    suspend fun deleteCost(cost: CostDto)

    @Query("SELECT * FROM CostDto ORDER BY id ASC")
    fun readAllData() : Flow<List<CostDto>>
}