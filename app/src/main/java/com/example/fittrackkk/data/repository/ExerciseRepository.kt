package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.local.dao.CustomExerciseDao
import com.example.fittrackkk.data.local.dao.ExerciseDao
import com.example.fittrackkk.data.model.CustomExercise
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.data.model.ExerciseDay
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val exerciseDao: ExerciseDao,
    private val customExerciseDao: CustomExerciseDao
) {

    fun getAllExercises(): Flow<List<Exercise>> = exerciseDao.getAllExercises()
    suspend fun getAllExercisesOnce(): List<Exercise> = exerciseDao.getAllExercisesOnce()
    suspend fun getExerciseById(id: Int): Exercise? = exerciseDao.getExerciseById(id)
    suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)
    suspend fun updateExercise(exercise: Exercise) = exerciseDao.updateExercise(exercise)
    suspend fun deleteExercise(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

    fun getAllExerciseDays(): Flow<List<ExerciseDay>> = exerciseDao.getAllExerciseDays()
    fun getExerciseDay(dayNumber: Int): Flow<ExerciseDay?> = exerciseDao.getExerciseDay(dayNumber)
    suspend fun getExerciseDayOnce(dayNumber: Int): ExerciseDay? = exerciseDao.getExerciseDayOnce(dayNumber)
    fun getCompletedDaysCount(): Flow<Int> = exerciseDao.getCompletedExerciseDaysCount()

    suspend fun markDayCompleted(dayNumber: Int, completed: Boolean) {
        exerciseDao.markExerciseDayCompleted(dayNumber, completed, if (completed) System.currentTimeMillis() else null)
    }

    suspend fun updateExerciseDay(exerciseDay: ExerciseDay) = exerciseDao.updateExerciseDay(exerciseDay)
    suspend fun insertAllExerciseDays(days: List<ExerciseDay>) = exerciseDao.insertAllExerciseDays(days)
    suspend fun insertAllExercises(exercises: List<Exercise>) = exerciseDao.insertAllExercises(exercises)

    suspend fun resetAllProgress() = exerciseDao.resetAllExerciseProgress()
    suspend fun deleteAllExerciseDays() = exerciseDao.deleteAllExerciseDays()
    suspend fun deleteAllExercises() = exerciseDao.deleteAllExercises()

    // Custom Exercise Operations
    fun getAllCustomExercises(): Flow<List<CustomExercise>> = customExerciseDao.getAllCustomExercises()
    suspend fun getCustomExerciseById(id: Int): CustomExercise? = customExerciseDao.getCustomExerciseById(id)
    suspend fun insertCustomExercise(exercise: CustomExercise) = customExerciseDao.insertCustomExercise(exercise)
    suspend fun updateCustomExercise(exercise: CustomExercise) = customExerciseDao.updateCustomExercise(exercise)
    suspend fun deleteCustomExercise(exercise: CustomExercise) = customExerciseDao.deleteCustomExercise(exercise)
}
