package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.dao.UserProfileDao
import com.example.fittrackkk.data.local.FitTrackDatabase
import com.example.fittrackkk.data.local.SeedData
import com.example.fittrackkk.data.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository(
    private val userProfileDao: UserProfileDao,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val firebaseDataManager: FirebaseDataManager
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    val currentUser get() = firebaseAuthManager.currentUser
    val isLoggedIn get() = firebaseAuthManager.isLoggedIn

    fun getProfile(): Flow<UserProfile?> = userProfileDao.getProfile()

    suspend fun getProfileOnce(): UserProfile? = userProfileDao.getProfileOnce()

    suspend fun isProfileComplete(): Boolean = userProfileDao.isProfileComplete() ?: false

    suspend fun signUp(email: String, password: String): Result<Unit> {
        val result = firebaseAuthManager.signUp(email, password)
        return result.map { }
    }

    suspend fun signIn(email: String, password: String): Result<Unit> {
        val result = firebaseAuthManager.signIn(email, password)
        return result.map { }
    }

    suspend fun saveProfile(profile: UserProfile) {
        userProfileDao.insertProfile(profile)
        // Sync to Firebase in background
        currentUser?.uid?.let { uid ->
            scope.launch {
                try { firebaseDataManager.saveUserProfile(uid, profile) } catch (_: Exception) {}
            }
        }
    }

    suspend fun updateProfile(profile: UserProfile) {
        val updated = profile.copy(updatedAt = System.currentTimeMillis())
        userProfileDao.updateProfile(updated)
        currentUser?.uid?.let { uid ->
            scope.launch {
                try { firebaseDataManager.saveUserProfile(uid, updated) } catch (_: Exception) {}
            }
        }
    }

    suspend fun deleteProfile() {
        userProfileDao.deleteProfile()
        currentUser?.uid?.let { uid ->
            scope.launch {
                try { firebaseDataManager.deleteUserProfile(uid) } catch (_: Exception) {}
            }
        }
    }

    suspend fun syncProfileFromFirebase() {
        currentUser?.uid?.let { uid ->
            try {
                val result = firebaseDataManager.getUserProfile(uid)
                result.getOrNull()?.let { profile ->
                    userProfileDao.insertProfile(profile)
                }
            } catch (_: Exception) {}
        }
    }

    fun signOut() {
        firebaseAuthManager.signOut()
    }

    // --- Account Deletion and Cache Clearing ---
    suspend fun clearLocalDataAndReseed(database: FitTrackDatabase) = withContext(Dispatchers.IO) {
        // Clear all tables
        database.userProfileDao().deleteProfile()
        database.dietDao().deleteAllDietDays()
        database.exerciseDao().deleteAllExercises()
        database.exerciseDao().deleteAllExerciseDays()
        database.recipeDao().deleteAllRecipes()
        database.healthArticleDao().deleteAllArticles()
        database.customMealDao().deleteAllCustomMeals()
        database.customExerciseDao().deleteAllCustomExercises()

        // Reseed default data
        database.dietDao().insertAllDietDays(SeedData.getDietDays())
        database.exerciseDao().insertAllExercises(SeedData.getExercises())
        database.exerciseDao().insertAllExerciseDays(SeedData.getExerciseDays())
        database.recipeDao().insertAllRecipes(SeedData.getRecipes())
        database.healthArticleDao().insertAllArticles(SeedData.getHealthArticles())
        database.customMealDao().insertAllCustomMeals(SeedData.getNepaliFoods())
    }

    suspend fun deleteAccount(database: FitTrackDatabase): Result<Unit> = withContext(Dispatchers.IO) {
        val userId = currentUser?.uid ?: return@withContext Result.failure(Exception("No user logged in"))
        try {
            // 1. Delete user doc & collections from Firestore
            firebaseDataManager.deleteAllUserData(userId).getOrThrow()

            // 2. Clear Room Database locally
            clearLocalDataAndReseed(database)

            // 3. Delete Firebase Authentication account
            val user = firebaseAuthManager.currentUser
            user?.delete()?.await()

            // 4. Sign out
            signOut()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
