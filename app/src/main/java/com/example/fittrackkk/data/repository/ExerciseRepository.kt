package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.dao.CustomExerciseDao
import com.example.fittrackkk.data.local.dao.ExerciseDao
import com.example.fittrackkk.data.model.CustomExercise
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.data.model.ExerciseDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExerciseRepository(
    private val exerciseDao: ExerciseDao,
    private val customExerciseDao: CustomExerciseDao,
    private val firebaseDataManager: FirebaseDataManager = FirebaseDataManager(),
    private val firebaseAuthManager: FirebaseAuthManager = FirebaseAuthManager()
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val currentUserId: String? get() = firebaseAuthManager.currentUser?.uid

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
        val completedAt = if (completed) System.currentTimeMillis() else null
        exerciseDao.markExerciseDayCompleted(dayNumber, completed, completedAt)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            getExerciseDayOnce(dayNumber)?.let { updatedDay ->
                scope.launch {
                    firebaseDataManager.saveExerciseDay(uid, updatedDay)
                }
            }
        }
    }

    suspend fun updateExerciseDay(exerciseDay: ExerciseDay) {
        exerciseDao.updateExerciseDay(exerciseDay)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveExerciseDay(uid, exerciseDay)
            }
        }
    }
    
    suspend fun insertAllExerciseDays(days: List<ExerciseDay>) = exerciseDao.insertAllExerciseDays(days)
    
    suspend fun insertAllExercises(exercises: List<Exercise>) = exerciseDao.insertAllExercises(exercises)

    suspend fun resetAllProgress() {
        exerciseDao.resetAllExerciseProgress()
        
        // Sync reset states in background
        currentUserId?.let { uid ->
            scope.launch {
                // Fetch and sync all days
                val allDays = mutableListOf<ExerciseDay>()
                for (i in 1..30) {
                    exerciseDao.getExerciseDayOnce(i)?.let { allDays.add(it) }
                }
                firebaseDataManager.saveAllExerciseDays(uid, allDays)
            }
        }
    }
    
    suspend fun deleteAllExerciseDays() = exerciseDao.deleteAllExerciseDays()
    
    suspend fun deleteAllExercises() = exerciseDao.deleteAllExercises()

    // --- Custom Exercise Operations ---
    fun getAllCustomExercises(): Flow<List<CustomExercise>> = customExerciseDao.getAllCustomExercises()
    
    suspend fun getCustomExerciseById(id: Int): CustomExercise? = customExerciseDao.getCustomExerciseById(id)
    
    suspend fun insertCustomExercise(exercise: CustomExercise) {
        customExerciseDao.insertCustomExercise(exercise)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveCustomExercise(uid, exercise)
            }
        }
    }
    
    suspend fun updateCustomExercise(exercise: CustomExercise) {
        customExerciseDao.updateCustomExercise(exercise)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveCustomExercise(uid, exercise)
            }
        }
    }
    
    suspend fun deleteCustomExercise(exercise: CustomExercise) {
        customExerciseDao.deleteCustomExercise(exercise)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.deleteCustomExercise(uid, exercise.id)
            }
        }
    }

    // --- Sync Method ---
    suspend fun syncFromFirebase(userId: String): Result<Unit> {
        return try {
            // 1. Sync Exercise Days
            val remoteDaysResult = firebaseDataManager.getExerciseDays(userId)
            remoteDaysResult.getOrNull()?.let { remoteDays ->
                if (remoteDays.isNotEmpty()) {
                    remoteDays.forEach { exerciseDao.insertExerciseDay(it) }
                }
            }
            
            // 2. Sync Custom Exercises
            val remoteExercisesResult = firebaseDataManager.getCustomExercises(userId)
            remoteExercisesResult.getOrNull()?.let { remoteExercises ->
                if (remoteExercises.isNotEmpty()) {
                    customExerciseDao.insertAllCustomExercises(remoteExercises)
                }
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
