package com.example.fittrackkk.data.repository

import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.dao.HealthArticleDao
import com.example.fittrackkk.data.local.dao.RecipeDao
import com.example.fittrackkk.data.model.HealthArticle
import com.example.fittrackkk.data.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DiscoverRepository(
    private val recipeDao: RecipeDao,
    private val healthArticleDao: HealthArticleDao,
    private val firebaseDataManager: FirebaseDataManager = FirebaseDataManager(),
    private val firebaseAuthManager: FirebaseAuthManager = FirebaseAuthManager()
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val currentUserId: String? get() = firebaseAuthManager.currentUser?.uid

    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    
    fun getRecipeById(id: Int): Flow<Recipe?> = recipeDao.getRecipeById(id)
    
    fun getRecipesByCategory(category: String): Flow<List<Recipe>> = recipeDao.getRecipesByCategory(category)
    
    fun getUserCreatedRecipes(): Flow<List<Recipe>> = recipeDao.getUserCreatedRecipes()
    
    suspend fun getRecipeByIdOnce(id: Int): Recipe? = recipeDao.getRecipeByIdOnce(id)

    suspend fun insertRecipe(recipe: Recipe) {
        val newId = recipeDao.insertRecipe(recipe).toInt()
        val finalRecipe = recipe.copy(id = newId)
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveRecipe(uid, finalRecipe)
            }
        }
    }
    
    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.saveRecipe(uid, recipe)
            }
        }
    }
    
    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
        currentUserId?.let { uid ->
            scope.launch {
                firebaseDataManager.deleteRecipe(uid, recipe.id)
            }
        }
    }

    fun getAllArticles(): Flow<List<HealthArticle>> = healthArticleDao.getAllArticles()
    
    fun getArticleById(id: Int): Flow<HealthArticle?> = healthArticleDao.getArticleById(id)
    
    suspend fun insertArticle(article: HealthArticle) = healthArticleDao.insertArticle(article)
    
    suspend fun updateArticle(article: HealthArticle) = healthArticleDao.updateArticle(article)
    
    suspend fun deleteArticle(article: HealthArticle) = healthArticleDao.deleteArticle(article)

    // --- Sync Method ---
    suspend fun syncFromFirebase(userId: String): Result<Unit> {
        return try {
            val remoteRecipesResult = firebaseDataManager.getRecipes(userId)
            remoteRecipesResult.getOrNull()?.let { remoteRecipes ->
                if (remoteRecipes.isNotEmpty()) {
                    recipeDao.insertAllRecipes(remoteRecipes)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
