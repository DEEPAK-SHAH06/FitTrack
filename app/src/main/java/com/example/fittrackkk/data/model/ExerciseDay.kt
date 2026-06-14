package com.example.fittrackkk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_day")
data class ExerciseDay(
    @PrimaryKey
    val dayNumber: Int,
    val exerciseIds: String = "", // Comma-separated default exercise IDs
    val customExerciseIds: String = "", // Comma-separated custom exercise IDs
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)
