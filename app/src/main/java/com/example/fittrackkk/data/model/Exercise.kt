package com.example.fittrackkk.data.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "exercise", indices = [Index(value = ["category"])])
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
