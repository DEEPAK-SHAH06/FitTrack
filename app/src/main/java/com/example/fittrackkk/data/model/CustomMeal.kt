package com.example.fittrackkk.data.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "custom_meals", indices = [Index(value = ["category"])])
data class CustomMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String, // Breakfast, Lunch, Snack, Dinner
    val calories: Int,
    val description: String = ""
)
