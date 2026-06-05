package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.data.model.ExerciseDay
import com.example.fittrackkk.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val exerciseRepository = ExerciseRepository(db.exerciseDao())

    val exerciseDays: StateFlow<List<ExerciseDay>> = exerciseRepository.getAllExerciseDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedDaysCount: StateFlow<Int> = exerciseRepository.getCompletedDaysCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _selectedDayExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val selectedDayExercises: StateFlow<List<Exercise>> = _selectedDayExercises

    private val _selectedExerciseDay = MutableStateFlow<ExerciseDay?>(null)
    val selectedExerciseDay: StateFlow<ExerciseDay?> = _selectedExerciseDay

    fun loadDayExercises(dayNumber: Int) {
        viewModelScope.launch {
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            _selectedExerciseDay.value = day
            day?.let {
                val ids = it.exerciseIds.split(",").mapNotNull { id -> id.trim().toIntOrNull() }
                val exercises = ids.mapNotNull { id -> exerciseRepository.getExerciseById(id) }
                _selectedDayExercises.value = exercises
            }
        }
    }

    fun markDayCompleted(dayNumber: Int) {
        viewModelScope.launch {
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            day?.let {
                exerciseRepository.markDayCompleted(dayNumber, !it.isCompleted)
                _selectedExerciseDay.value = it.copy(isCompleted = !it.isCompleted)
            }
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch { exerciseRepository.resetAllProgress() }
    }
}
