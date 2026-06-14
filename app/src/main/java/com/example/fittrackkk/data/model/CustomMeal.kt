package com.example.fittrackkk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_meals")
data class CustomMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String, // Breakfast, Lunch, Snack, Dinner
    val calories: Int,
    val description: String = ""
)
