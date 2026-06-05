package com.example.fittrackkk.data.local.dao

import androidx.room.*
import com.example.fittrackkk.data.model.DietDay
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDietDay(dietDay: DietDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDietDays(days: List<DietDay>)

    @Query("SELECT * FROM diet_day ORDER BY dayNumber ASC")
    fun getAllDietDays(): Flow<List<DietDay>>

    @Query("SELECT * FROM diet_day WHERE dayNumber = :dayNumber")
    fun getDietDay(dayNumber: Int): Flow<DietDay?>

    @Query("SELECT * FROM diet_day WHERE dayNumber = :dayNumber")
    suspend fun getDietDayOnce(dayNumber: Int): DietDay?

    @Update
    suspend fun updateDietDay(dietDay: DietDay)

    @Query("UPDATE diet_day SET isCompleted = :isCompleted, completedAt = :completedAt WHERE dayNumber = :dayNumber")
    suspend fun markDayCompleted(dayNumber: Int, isCompleted: Boolean, completedAt: Long?)

    @Query("SELECT COUNT(*) FROM diet_day WHERE isCompleted = 1")
    fun getCompletedDietDaysCount(): Flow<Int>

    @Query("DELETE FROM diet_day")
    suspend fun deleteAllDietDays()

    @Query("UPDATE diet_day SET isCompleted = 0, completedAt = null")
    suspend fun resetAllProgress()
}
