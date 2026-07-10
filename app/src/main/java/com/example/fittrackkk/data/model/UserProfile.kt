package com.example.fittrackkk.data.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: String = "current_user",
    val email: String = "",
    val height: Float = 0f,
    val weight: Float = 0f,
    val targetWeight: Float = 0f,
    val age: Int = 0,
    val gender: String = "",
    val isProfileComplete: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val bmi: Float
        get() {
            if (height <= 0f || weight <= 0f) return 0f
            val heightInMeters = height / 100f
            return weight / (heightInMeters * heightInMeters)
        }
}
