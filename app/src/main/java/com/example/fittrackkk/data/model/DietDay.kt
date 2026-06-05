package com.example.fittrackkk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diet_day")
data class DietDay(
    @PrimaryKey
    val dayNumber: Int,
    val breakfast: String = "",
    val breakfastCalories: Int = 0,
    val lunch: String = "",
    val lunchCalories: Int = 0,
    val afternoonSnack: String = "",
    val afternoonSnackCalories: Int = 0,
    val dinner: String = "",
    val dinnerCalories: Int = 0,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
) {
    val totalCalories: Int
        get() = breakfastCalories + lunchCalories + afternoonSnackCalories + dinnerCalories
}
