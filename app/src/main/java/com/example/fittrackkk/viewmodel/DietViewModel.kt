package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.local.SeedData
import com.example.fittrackkk.data.model.CustomMeal
import com.example.fittrackkk.data.model.DietDay
import com.example.fittrackkk.data.repository.DietRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DietViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val dietRepository = DietRepository(db.dietDao(), db.customMealDao())

    init {
        viewModelScope.launch {
            dietRepository.getAllDietDays().first().let { days ->
                if (days.isEmpty()) {
                    dietRepository.insertAllDietDays(SeedData.getDietDays())
                }
            }
        }
    }

    val dietDays: StateFlow<List<DietDay>> = dietRepository.getAllDietDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customMeals: StateFlow<List<CustomMeal>> = dietRepository.getAllCustomMeals()
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

    // Custom Meal CRUD
    fun addCustomMeal(name: String, category: String, calories: Int, description: String) {
        viewModelScope.launch {
            dietRepository.insertCustomMeal(CustomMeal(name = name, category = category, calories = calories, description = description))
        }
    }

    fun updateCustomMeal(meal: CustomMeal) {
        viewModelScope.launch {
            dietRepository.updateCustomMeal(meal)
        }
    }

    fun deleteCustomMeal(meal: CustomMeal) {
        viewModelScope.launch {
            dietRepository.deleteCustomMeal(meal)
        }
    }

    // Edit Plan
    fun updateDayMeal(dayNumber: Int, mealType: String, customMealId: Int?) {
        viewModelScope.launch {
            val day = dietRepository.getDietDayOnce(dayNumber)
            val defaultDay = SeedData.getDietDays().find { it.dayNumber == dayNumber }
            
            day?.let {
                val customMeal = customMealId?.let { id -> dietRepository.getCustomMealById(id) }
                val updatedDay = when (mealType) {
                    "Breakfast" -> it.copy(
                        breakfast = customMeal?.name ?: defaultDay?.breakfast ?: it.breakfast,
                        breakfastCalories = customMeal?.calories ?: defaultDay?.breakfastCalories ?: it.breakfastCalories,
                        breakfastCustomId = customMealId
                    )
                    "Lunch" -> it.copy(
                        lunch = customMeal?.name ?: defaultDay?.lunch ?: it.lunch,
                        lunchCalories = customMeal?.calories ?: defaultDay?.lunchCalories ?: it.lunchCalories,
                        lunchCustomId = customMealId
                    )
                    "Snack" -> it.copy(
                        afternoonSnack = customMeal?.name ?: defaultDay?.afternoonSnack ?: it.afternoonSnack,
                        afternoonSnackCalories = customMeal?.calories ?: defaultDay?.afternoonSnackCalories ?: it.afternoonSnackCalories,
                        afternoonSnackCustomId = customMealId
                    )
                    "Dinner" -> it.copy(
                        dinner = customMeal?.name ?: defaultDay?.dinner ?: it.dinner,
                        dinnerCalories = customMeal?.calories ?: defaultDay?.dinnerCalories ?: it.dinnerCalories,
                        dinnerCustomId = customMealId
                    )
                    else -> it
                }
                dietRepository.updateDietDay(updatedDay)
                _selectedDay.value = updatedDay
            }
        }
    }
}
