package com.example.fittrackkk.data.local.dao

import androidx.room.*
import com.example.fittrackkk.data.model.CustomMeal
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomMealDao {
    @Query("SELECT * FROM custom_meals")
    fun getAllCustomMeals(): Flow<List<CustomMeal>>

    @Query("SELECT * FROM custom_meals WHERE id = :id")
    suspend fun getCustomMealById(id: Int): CustomMeal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomMeal(meal: CustomMeal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCustomMeals(meals: List<CustomMeal>)

    @Update
    suspend fun updateCustomMeal(meal: CustomMeal)

    @Delete
    suspend fun deleteCustomMeal(meal: CustomMeal)

    @Query("DELETE FROM custom_meals")
    suspend fun deleteAllCustomMeals()
}
