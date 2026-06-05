package com.example.fittrackkk.data.local.dao

import androidx.room.*
import com.example.fittrackkk.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfile)

    @Query("SELECT * FROM user_profile WHERE id = 'current_user'")
    fun getProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 'current_user'")
    suspend fun getProfileOnce(): UserProfile?

    @Update
    suspend fun updateProfile(profile: UserProfile)

    @Query("DELETE FROM user_profile")
    suspend fun deleteProfile()

    @Query("SELECT isProfileComplete FROM user_profile WHERE id = 'current_user'")
    suspend fun isProfileComplete(): Boolean?
}
