package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.dao.UserProfileDao
import com.example.fittrackkk.data.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
        if (result.isSuccess) {
            // Try to sync profile from Firebase in background
            scope.launch {
                syncProfileFromFirebase()
            }
        }
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

    private suspend fun syncProfileFromFirebase() {
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
}
