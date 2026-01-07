package com.example.skillsbuild.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {
    @Insert
    suspend fun insert(calc: Calculation): Long

    @Query("SELECT * FROM calculations ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Calculation>>

    @Query("DELETE FROM calculations")
    suspend fun clearAll()
}

