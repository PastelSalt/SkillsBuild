package com.example.skillsbuild.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.skillsbuild.data.AppDatabase
import com.example.skillsbuild.data.CalculationRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CalculationRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = CalculationRepository(db.calculationDao())
    }

    val history = repository.getHistory().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addCalculation(expression: String, result: String, timestamp: Long) {
        viewModelScope.launch {
            repository.addCalculation(expression, result, timestamp)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}

