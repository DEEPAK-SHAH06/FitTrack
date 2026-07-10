package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.model.HealthArticle
import com.example.fittrackkk.data.model.Recipe
import com.example.fittrackkk.data.repository.DiscoverRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DiscoverViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val discoverRepository = DiscoverRepository(db.recipeDao(), db.healthArticleDao())

    init {
        viewModelScope.launch {
            discoverRepository.getAllRecipes().first().let { recipes ->
                if (recipes.isEmpty()) {
                    val seedRecipes = com.example.fittrackkk.data.local.SeedData.getRecipes()
                    // Insert seed recipes without setting isUserCreated (it defaults to false)
                    seedRecipes.forEach { discoverRepository.insertRecipe(it) }
                }
            }
            discoverRepository.getAllArticles().first().let { articles ->
                if (articles.isEmpty()) {
                    val seedArticles = com.example.fittrackkk.data.local.SeedData.getHealthArticles()
                    seedArticles.forEach { discoverRepository.insertArticle(it) }
                }
            }
        }
    }

    val recipes: StateFlow<List<Recipe>> = discoverRepository.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userCreatedRecipes: StateFlow<List<Recipe>> = discoverRepository.getUserCreatedRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val articles: StateFlow<List<HealthArticle>> = discoverRepository.getAllArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    private val _selectedArticle = MutableStateFlow<HealthArticle?>(null)
    val selectedArticle: StateFlow<HealthArticle?> = _selectedArticle

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            discoverRepository.getRecipeById(id).collect { _selectedRecipe.value = it }
        }
    }

    suspend fun getRecipeByIdOnce(id: Int): Recipe? {
        return discoverRepository.getRecipeByIdOnce(id)
    }

    fun addRecipe(
        title: String,
        category: String,
        ingredients: String,
        steps: String,
        calories: Int,
        prepTimeMinutes: Int,
        imageUrl: String
    ) {
        viewModelScope.launch {
            val newRecipe = Recipe(
                title = title,
                category = category,
                ingredients = ingredients,
                steps = steps,
                calories = calories,
                prepTimeMinutes = prepTimeMinutes,
                imageUrl = imageUrl,
                isUserCreated = true
            )
            discoverRepository.insertRecipe(newRecipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            discoverRepository.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            discoverRepository.deleteRecipe(recipe)
        }
    }

    fun loadArticle(id: Int) {
        viewModelScope.launch {
            discoverRepository.getArticleById(id).collect { _selectedArticle.value = it }
        }
    }
}
