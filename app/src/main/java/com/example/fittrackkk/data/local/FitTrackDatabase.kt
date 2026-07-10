package com.example.fittrackkk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fittrackkk.data.local.dao.*
import com.example.fittrackkk.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfile::class, 
        DietDay::class, 
        Exercise::class, 
        ExerciseDay::class, 
        Recipe::class, 
        HealthArticle::class,
        CustomMeal::class,
        CustomExercise::class
    ],
    version = 6,
    exportSchema = false
)
abstract class FitTrackDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun dietDao(): DietDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun recipeDao(): RecipeDao
    abstract fun healthArticleDao(): HealthArticleDao
    abstract fun customMealDao(): CustomMealDao
    abstract fun customExerciseDao(): CustomExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: FitTrackDatabase? = null

        fun getDatabase(context: Context): FitTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitTrackDatabase::class.java,
                    "fittrack_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database)
                }
            }
        }

        private suspend fun populateDatabase(db: FitTrackDatabase) {
            // Populate diet days
            db.dietDao().insertAllDietDays(SeedData.getDietDays())
            // Populate exercises
            db.exerciseDao().insertAllExercises(SeedData.getExercises())
            // Populate exercise days
            db.exerciseDao().insertAllExerciseDays(SeedData.getExerciseDays())
            // Populate recipes
            db.recipeDao().insertAllRecipes(SeedData.getRecipes())
            // Populate health articles
            db.healthArticleDao().insertAllArticles(SeedData.getHealthArticles())
            // Populate Nepali foods
            db.customMealDao().insertAllCustomMeals(SeedData.getNepaliFoods())
        }
    }
}
