package com.example.fittrackkk.data.local.dao

import androidx.room.*
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.data.model.ExerciseDay
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    // Exercise pool operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExercises(exercises: List<Exercise>)

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise")
    suspend fun getAllExercisesOnce(): List<Exercise>

    @Query("SELECT * FROM exercise WHERE id = :id")
    suspend fun getExerciseById(id: Int): Exercise?

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM exercise")
    suspend fun deleteAllExercises()

    // Exercise day operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseDay(exerciseDay: ExerciseDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExerciseDays(days: List<ExerciseDay>)

    @Query("SELECT * FROM exercise_day ORDER BY dayNumber ASC")
    fun getAllExerciseDays(): Flow<List<ExerciseDay>>

    @Query("SELECT * FROM exercise_day WHERE dayNumber = :dayNumber")
    fun getExerciseDay(dayNumber: Int): Flow<ExerciseDay?>

    @Query("SELECT * FROM exercise_day WHERE dayNumber = :dayNumber")
    suspend fun getExerciseDayOnce(dayNumber: Int): ExerciseDay?

    @Update
    suspend fun updateExerciseDay(exerciseDay: ExerciseDay)

    @Query("UPDATE exercise_day SET isCompleted = :isCompleted, completedAt = :completedAt WHERE dayNumber = :dayNumber")
    suspend fun markExerciseDayCompleted(dayNumber: Int, isCompleted: Boolean, completedAt: Long?)

    @Query("SELECT COUNT(*) FROM exercise_day WHERE isCompleted = 1")
    fun getCompletedExerciseDaysCount(): Flow<Int>

    @Query("DELETE FROM exercise_day")
    suspend fun deleteAllExerciseDays()

    @Query("UPDATE exercise_day SET isCompleted = 0, completedAt = null")
    suspend fun resetAllExerciseProgress()
}
