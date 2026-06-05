package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.local.dao.HealthArticleDao
import com.example.fittrackkk.data.local.dao.RecipeDao
import com.example.fittrackkk.data.model.HealthArticle
import com.example.fittrackkk.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class DiscoverRepository(
    private val recipeDao: RecipeDao,
    private val healthArticleDao: HealthArticleDao
) {
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    fun getRecipeById(id: Int): Flow<Recipe?> = recipeDao.getRecipeById(id)
    fun getRecipesByCategory(category: String): Flow<List<Recipe>> = recipeDao.getRecipesByCategory(category)
    suspend fun insertRecipe(recipe: Recipe) = recipeDao.insertRecipe(recipe)
    suspend fun updateRecipe(recipe: Recipe) = recipeDao.updateRecipe(recipe)
    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)

    fun getAllArticles(): Flow<List<HealthArticle>> = healthArticleDao.getAllArticles()
    fun getArticleById(id: Int): Flow<HealthArticle?> = healthArticleDao.getArticleById(id)
    suspend fun insertArticle(article: HealthArticle) = healthArticleDao.insertArticle(article)
    suspend fun updateArticle(article: HealthArticle) = healthArticleDao.updateArticle(article)
    suspend fun deleteArticle(article: HealthArticle) = healthArticleDao.deleteArticle(article)
}
