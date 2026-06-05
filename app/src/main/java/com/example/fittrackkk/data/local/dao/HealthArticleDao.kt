package com.example.fittrackkk.data.local.dao

import androidx.room.*
import com.example.fittrackkk.data.model.HealthArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: HealthArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<HealthArticle>)

    @Query("SELECT * FROM health_article")
    fun getAllArticles(): Flow<List<HealthArticle>>

    @Query("SELECT * FROM health_article WHERE id = :id")
    fun getArticleById(id: Int): Flow<HealthArticle?>

    @Update
    suspend fun updateArticle(article: HealthArticle)

    @Delete
    suspend fun deleteArticle(article: HealthArticle)

    @Query("DELETE FROM health_article")
    suspend fun deleteAllArticles()
}
