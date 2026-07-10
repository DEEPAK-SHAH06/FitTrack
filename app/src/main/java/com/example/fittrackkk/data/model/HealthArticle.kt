package com.example.fittrackkk.data.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "health_article")
data class HealthArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String = "",
    val readTimeMinutes: Float = 3f,
    val category: String = "General",
    val imageDescription: String = ""
)
