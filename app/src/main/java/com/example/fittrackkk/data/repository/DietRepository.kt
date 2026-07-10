package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.dao.CustomMealDao
import com.example.fittrackkk.data.local.dao.DietDao
import com.example.fittrackkk.data.model.CustomMeal
import com.example.fittrackkk.data.model.DietDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DietRepository(
    private val dietDao: DietDao,
    private val customMealDao: CustomMealDao,
    private val firebaseDataManager: FirebaseDataManager = FirebaseDataManager(),
    private val firebaseAuthManager: FirebaseAuthManager = FirebaseAuthManager()
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val currentUserId: String? get() = firebaseAuthManager.currentUser?.uid

    fun getAllDietDays(): Flow<List<DietDay>> = dietDao.getAllDietDays()

    fun getDietDay(dayNumber: Int): Flow<DietDay?> = dietDao.getDietDay(dayNumber)

    suspend fun getDietDayOnce(dayNumber: Int): DietDay? = dietDao.getDietDayOnce(dayNumber)

    fun getCompletedDaysCount(): Flow<Int> = dietDao.getCompletedDietDaysCount()

    suspend fun markDayCompleted(dayNumber: Int, completed: Boolean) {
        val completedAt = if (completed) System.currentTimeMillis() else null
        dietDao.markDayCompleted(dayNumber, completed, completedAt)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            getDietDayOnce(dayNumber)?.let { updatedDay ->
                scope.launch {
                    firebaseDataManager.saveDietDay(uid, updatedDay)
                }
            }
        }
    }

    suspend fun updateDietDay(dietDay: DietDay) {
        dietDao.updateDietDay(dietDay)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveDietDay(uid, dietDay)
            }
        }
    }

    suspend fun resetAllProgress() {
        dietDao.resetAllProgress()
        
        // Sync reset states in background
        currentUserId?.let { uid ->
            scope.launch {
                val days = dietDao.getDietDayOnce(1)?.let {
                    // Fetch all diet days from local DB and sync
                    val allDays = mutableListOf<DietDay>()
                    for (i in 1..30) {
                        dietDao.getDietDayOnce(i)?.let { allDays.add(it) }
                    }
                    firebaseDataManager.saveAllDietDays(uid, allDays)
                }
            }
        }
    }

    suspend fun deleteAllDietDays() = dietDao.deleteAllDietDays()

    suspend fun insertDietDay(dietDay: DietDay) = dietDao.insertDietDay(dietDay)

    suspend fun insertAllDietDays(days: List<DietDay>) = dietDao.insertAllDietDays(days)

    // --- Custom Meal Operations ---
    fun getAllCustomMeals(): Flow<List<CustomMeal>> = customMealDao.getAllCustomMeals()
    
    suspend fun getCustomMealById(id: Int): CustomMeal? = customMealDao.getCustomMealById(id)
    
    suspend fun insertCustomMeal(meal: CustomMeal) {
        customMealDao.insertCustomMeal(meal)
        
        // Sync to Firestore in background
        currentUserId?.let { uid ->
            scope.launch {
                // Since ID might be auto-generated, we find it or upload it
                // To handle auto-increment, we can query local DB and upload
                // Let's just upload this meal.
                firebaseDataManager.saveCustomMeal(uid, meal)
            }
        }
    }
    
    suspend fun updateCustomMeal(meal: CustomMeal) {
        customMealDao.updateCustomMeal(meal)
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveCustomMeal(uid, meal)
            }
        }
    }
    
    suspend fun deleteCustomMeal(meal: CustomMeal) {
        customMealDao.deleteCustomMeal(meal)
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.deleteCustomMeal(uid, meal.id)
            }
        }
    }

    // --- Sync Method ---
    suspend fun syncFromFirebase(userId: String): Result<Unit> {
        return try {
            // 1. Sync Diet Days
            val remoteDaysResult = firebaseDataManager.getDietDays(userId)
            remoteDaysResult.getOrNull()?.let { remoteDays ->
                if (remoteDays.isNotEmpty()) {
                    remoteDays.forEach { dietDao.insertDietDay(it) }
                }
            }
            
            // 2. Sync Custom Meals
            val remoteMealsResult = firebaseDataManager.getCustomMeals(userId)
            remoteMealsResult.getOrNull()?.let { remoteMeals ->
                if (remoteMeals.isNotEmpty()) {
                    customMealDao.insertAllCustomMeals(remoteMeals)
                }
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
