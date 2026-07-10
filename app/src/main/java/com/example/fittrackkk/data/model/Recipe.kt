package com.example.fittrackkk.data.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "recipe", indices = [Index(value = ["category"])])
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val category: String = "Breakfast", // Breakfast, Lunch, Snack, Dinner
    val ingredients: String = "", // Newline-separated
    val steps: String = "", // Newline-separated
    val calories: Int = 0,
    val prepTimeMinutes: Int = 0,
    val imageDescription: String = "", // For UI placeholder
    val imageUrl: String = "", // User-provided image URL for Coil loading
    val isUserCreated: Boolean = false // Distinguishes seed vs user-created recipes
)
