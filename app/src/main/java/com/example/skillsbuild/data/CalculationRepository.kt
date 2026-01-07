package com.example.skillsbuild.data

import kotlinx.coroutines.flow.Flow

class CalculationRepository(private val dao: CalculationDao) {
    suspend fun addCalculation(expression: String, result: String, timestamp: Long) {
        dao.insert(Calculation(expression = expression, result = result, timestamp = timestamp))
    }

    fun getHistory(): Flow<List<Calculation>> = dao.getAll()

    suspend fun clearHistory() = dao.clearAll()
}

