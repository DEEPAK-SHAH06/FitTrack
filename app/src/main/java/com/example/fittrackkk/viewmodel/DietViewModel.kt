package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.model.DietDay
import com.example.fittrackkk.data.repository.DietRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DietViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val dietRepository = DietRepository(db.dietDao())

    val dietDays: StateFlow<List<DietDay>> = dietRepository.getAllDietDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedDaysCount: StateFlow<Int> = dietRepository.getCompletedDaysCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _selectedDay = MutableStateFlow<DietDay?>(null)
    val selectedDay: StateFlow<DietDay?> = _selectedDay

    fun loadDay(dayNumber: Int) {
        viewModelScope.launch {
            dietRepository.getDietDay(dayNumber).collect { _selectedDay.value = it }
        }
    }

    fun markDayCompleted(dayNumber: Int) {
        viewModelScope.launch {
            val day = dietRepository.getDietDayOnce(dayNumber)
            day?.let {
                dietRepository.markDayCompleted(dayNumber, !it.isCompleted)
            }
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch { dietRepository.resetAllProgress() }
    }
}
