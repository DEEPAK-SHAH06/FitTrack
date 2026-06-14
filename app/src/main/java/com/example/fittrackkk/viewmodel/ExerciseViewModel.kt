package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.model.CustomExercise
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.data.model.ExerciseDay
import com.example.fittrackkk.data.repository.DietRepository
import com.example.fittrackkk.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val exerciseRepository = ExerciseRepository(db.exerciseDao(), db.customExerciseDao())
    private val dietRepository = DietRepository(db.dietDao(), db.customMealDao())

    init {
        viewModelScope.launch {
            exerciseRepository.getAllExercises().first().let { exercises ->
                if (exercises.isEmpty()) {
                    exerciseRepository.insertAllExercises(com.example.fittrackkk.data.local.SeedData.getExercises())
                }
            }
            exerciseRepository.getAllExerciseDays().first().let { days ->
                if (days.isEmpty()) {
                    exerciseRepository.insertAllExerciseDays(com.example.fittrackkk.data.local.SeedData.getExerciseDays())
                }
            }
        }
    }

    val exerciseDays: StateFlow<List<ExerciseDay>> = exerciseRepository.getAllExerciseDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customExercises: StateFlow<List<CustomExercise>> = exerciseRepository.getAllCustomExercises()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allDefaultExercises: StateFlow<List<Exercise>> = exerciseRepository.getAllExercises()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedDaysCount: StateFlow<Int> = exerciseRepository.getCompletedDaysCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _selectedDayExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val selectedDayExercises: StateFlow<List<Exercise>> = _selectedDayExercises

    private val _selectedDayCustomExercises = MutableStateFlow<List<CustomExercise>>(emptyList())
    val selectedDayCustomExercises: StateFlow<List<CustomExercise>> = _selectedDayCustomExercises

    private val _selectedExerciseDay = MutableStateFlow<ExerciseDay?>(null)
    val selectedExerciseDay: StateFlow<ExerciseDay?> = _selectedExerciseDay

    fun loadDayExercises(dayNumber: Int) {
        viewModelScope.launch {
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            _selectedExerciseDay.value = day
            day?.let {
                val ids = it.exerciseIds.split(",").filter { s -> s.isNotBlank() }.mapNotNull { id -> id.trim().toIntOrNull() }
                val exercises = ids.mapNotNull { id -> exerciseRepository.getExerciseById(id) }
                _selectedDayExercises.value = exercises

                val customIds = it.customExerciseIds.split(",").filter { s -> s.isNotBlank() }.mapNotNull { id -> id.trim().toIntOrNull() }
                val customExercises = customIds.mapNotNull { id -> exerciseRepository.getCustomExerciseById(id) }
                _selectedDayCustomExercises.value = customExercises
            }
        }
    }

    fun markDayCompleted(dayNumber: Int) {
        viewModelScope.launch {
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            day?.let {
                val newState = !it.isCompleted
                exerciseRepository.markDayCompleted(dayNumber, newState)
                // Remove dietRepository.markDayCompleted here to decouple them
                _selectedExerciseDay.value = it.copy(isCompleted = newState)
            }
        }
    }

    fun completeWorkout(dayNumber: Int) {
        viewModelScope.launch {
            exerciseRepository.markDayCompleted(dayNumber, true)
            // Remove dietRepository.markDayCompleted here to decouple them
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            _selectedExerciseDay.value = day
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch { exerciseRepository.resetAllProgress() }
    }

    // Custom Exercise CRUD
    fun addCustomExercise(name: String, durationMinutes: Int, description: String) {
        viewModelScope.launch {
            exerciseRepository.insertCustomExercise(CustomExercise(name = name, durationMinutes = durationMinutes, description = description))
        }
    }

    fun updateCustomExercise(exercise: CustomExercise) {
        viewModelScope.launch {
            exerciseRepository.updateCustomExercise(exercise)
        }
    }

    fun deleteCustomExercise(exercise: CustomExercise) {
        viewModelScope.launch {
            exerciseRepository.deleteCustomExercise(exercise)
        }
    }

    // Edit Plan
    fun addExerciseToDay(dayNumber: Int, exerciseId: Int, isCustom: Boolean) {
        viewModelScope.launch {
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            day?.let {
                val updatedDay = if (isCustom) {
                    val currentIds = it.customExerciseIds.split(",").filter { s -> s.isNotBlank() }.toMutableList()
                    currentIds.add(exerciseId.toString())
                    it.copy(customExerciseIds = currentIds.joinToString(","))
                } else {
                    val currentIds = it.exerciseIds.split(",").filter { s -> s.isNotBlank() }.toMutableList()
                    currentIds.add(exerciseId.toString())
                    it.copy(exerciseIds = currentIds.joinToString(","))
                }
                exerciseRepository.updateExerciseDay(updatedDay)
                loadDayExercises(dayNumber)
            }
        }
    }

    fun removeExerciseFromDay(dayNumber: Int, exerciseId: Int, isCustom: Boolean) {
        viewModelScope.launch {
            val day = exerciseRepository.getExerciseDayOnce(dayNumber)
            day?.let {
                val updatedDay = if (isCustom) {
                    val currentIds = it.customExerciseIds.split(",").filter { s -> s.isNotBlank() }.toMutableList()
                    currentIds.remove(exerciseId.toString())
                    it.copy(customExerciseIds = currentIds.joinToString(","))
                } else {
                    val currentIds = it.exerciseIds.split(",").filter { s -> s.isNotBlank() }.toMutableList()
                    currentIds.remove(exerciseId.toString())
                    it.copy(exerciseIds = currentIds.joinToString(","))
                }
                exerciseRepository.updateExerciseDay(updatedDay)
                loadDayExercises(dayNumber)
            }
        }
    }
}
