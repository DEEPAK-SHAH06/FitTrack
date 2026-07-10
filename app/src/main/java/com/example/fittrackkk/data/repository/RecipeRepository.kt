package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.local.dao.RecipeDao
import com.example.fittrackkk.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    
    fun getRecipeById(id: Int): Flow<Recipe?> = recipeDao.getRecipeById(id)
    
    suspend fun insertRecipe(recipe: Recipe): Long = recipeDao.insertRecipe(recipe)
    
    suspend fun updateRecipe(recipe: Recipe) = recipeDao.updateRecipe(recipe)
    
    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)
}
