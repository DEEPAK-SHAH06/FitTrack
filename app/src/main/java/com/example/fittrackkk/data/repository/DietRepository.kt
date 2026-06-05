package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.local.dao.DietDao
import com.example.fittrackkk.data.model.DietDay
import kotlinx.coroutines.flow.Flow

class DietRepository(private val dietDao: DietDao) {

    fun getAllDietDays(): Flow<List<DietDay>> = dietDao.getAllDietDays()

    fun getDietDay(dayNumber: Int): Flow<DietDay?> = dietDao.getDietDay(dayNumber)

    suspend fun getDietDayOnce(dayNumber: Int): DietDay? = dietDao.getDietDayOnce(dayNumber)

    fun getCompletedDaysCount(): Flow<Int> = dietDao.getCompletedDietDaysCount()

    suspend fun markDayCompleted(dayNumber: Int, completed: Boolean) {
        dietDao.markDayCompleted(dayNumber, completed, if (completed) System.currentTimeMillis() else null)
    }

    suspend fun updateDietDay(dietDay: DietDay) = dietDao.updateDietDay(dietDay)

    suspend fun resetAllProgress() = dietDao.resetAllProgress()

    suspend fun deleteAllDietDays() = dietDao.deleteAllDietDays()

    suspend fun insertDietDay(dietDay: DietDay) = dietDao.insertDietDay(dietDay)

    suspend fun insertAllDietDays(days: List<DietDay>) = dietDao.insertAllDietDays(days)
}
