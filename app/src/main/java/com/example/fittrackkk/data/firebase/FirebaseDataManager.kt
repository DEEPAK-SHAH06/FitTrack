package com.example.fittrackkk.data.firebase

import com.example.fittrackkk.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDataManager {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    suspend fun saveUserProfile(userId: String, profile: UserProfile): Result<Unit> {
        return try {
            val data = hashMapOf(
                "email" to profile.email,
                "height" to profile.height,
                "weight" to profile.weight,
                "targetWeight" to profile.targetWeight,
                "age" to profile.age,
                "gender" to profile.gender,
                "isProfileComplete" to profile.isProfileComplete,
                "updatedAt" to System.currentTimeMillis()
            )
            usersCollection.document(userId).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(userId: String): Result<UserProfile?> {
        return try {
            val doc = usersCollection.document(userId).get().await()
            if (doc.exists()) {
                val profile = UserProfile(
                    id = "current_user",
                    email = doc.getString("email") ?: "",
                    height = (doc.getDouble("height") ?: 0.0).toFloat(),
                    weight = (doc.getDouble("weight") ?: 0.0).toFloat(),
                    targetWeight = (doc.getDouble("targetWeight") ?: 0.0).toFloat(),
                    age = (doc.getLong("age") ?: 0).toInt(),
                    gender = doc.getString("gender") ?: "",
                    isProfileComplete = doc.getBoolean("isProfileComplete") ?: false
                )
                Result.success(profile)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUserProfile(userId: String): Result<Unit> {
        return try {
            usersCollection.document(userId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
