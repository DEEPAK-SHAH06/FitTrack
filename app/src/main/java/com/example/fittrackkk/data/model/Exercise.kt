package com.example.fittrackkk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String = "",
    val durationSeconds: Int = 90,
    val category: String = "General",
    val difficulty: String = "Beginner",
    val caloriesBurned: Int = 5,
    val iconName: String = ""
)
