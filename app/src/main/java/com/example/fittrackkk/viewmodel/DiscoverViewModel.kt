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

    val recipes: StateFlow<List<Recipe>> = discoverRepository.getAllRecipes()
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

    fun loadArticle(id: Int) {
        viewModelScope.launch {
            discoverRepository.getArticleById(id).collect { _selectedArticle.value = it }
        }
    }
}
