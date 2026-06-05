package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.local.dao.ExerciseDao
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.data.model.ExerciseDay
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

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

    suspend fun resetAllProgress() = exerciseDao.resetAllExerciseProgress()
    suspend fun deleteAllExerciseDays() = exerciseDao.deleteAllExerciseDays()
    suspend fun deleteAllExercises() = exerciseDao.deleteAllExercises()
}
