package com.example.fittrackkk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val category: String = "Breakfast", // Breakfast, Lunch, Snack, Dinner
    val ingredients: String = "", // Newline-separated
    val steps: String = "", // Newline-separated
    val calories: Int = 0,
    val prepTimeMinutes: Int = 0,
    val imageDescription: String = "" // For UI placeholder
)
