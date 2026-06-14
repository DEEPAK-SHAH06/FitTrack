package com.example.fittrackkk.data.local.dao

import androidx.room.*
import com.example.fittrackkk.data.model.CustomExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomExerciseDao {
    @Query("SELECT * FROM custom_exercises")
    fun getAllCustomExercises(): Flow<List<CustomExercise>>

    @Query("SELECT * FROM custom_exercises WHERE id = :id")
    suspend fun getCustomExerciseById(id: Int): CustomExercise?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomExercise(exercise: CustomExercise)

    @Update
    suspend fun updateCustomExercise(exercise: CustomExercise)

    @Delete
    suspend fun deleteCustomExercise(exercise: CustomExercise)
}
