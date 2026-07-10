package com.example.fittrackkk.data.firebase

import com.example.fittrackkk.data.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDataManager {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    // --- User Profile ---
    suspend fun saveUserProfile(userId: String, profile: UserProfile): Result<Unit> {
        return try {
            val data = hashMapOf(
                "email" to profile.email,
                "height" to profile.height,
                "weight" to profile.weight,
                "targetWeight" to profile.targetWeight,
                "age" to profile.age,
                "gender" to profile.gender,
                "isProfileComplete" to profile.isProfileComplete,
                "updatedAt" to System.currentTimeMillis()
            )
            usersCollection.document(userId).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(userId: String): Result<UserProfile?> {
        return try {
            val doc = usersCollection.document(userId).get().await()
            if (doc.exists()) {
                val profile = UserProfile(
                    id = "current_user",
                    email = doc.getString("email") ?: "",
                    height = (doc.getDouble("height") ?: 0.0).toFloat(),
                    weight = (doc.getDouble("weight") ?: 0.0).toFloat(),
                    targetWeight = (doc.getDouble("targetWeight") ?: 0.0).toFloat(),
                    age = (doc.getLong("age") ?: 0).toInt(),
                    gender = doc.getString("gender") ?: "",
                    isProfileComplete = doc.getBoolean("isProfileComplete") ?: false
                )
                Result.success(profile)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUserProfile(userId: String): Result<Unit> {
        return try {
            usersCollection.document(userId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Diet Days ---
    suspend fun saveDietDay(userId: String, day: DietDay): Result<Unit> {
        return try {
            val data = hashMapOf(
                "dayNumber" to day.dayNumber,
                "breakfast" to day.breakfast,
                "breakfastCalories" to day.breakfastCalories,
                "breakfastCustomId" to day.breakfastCustomId,
                "lunch" to day.lunch,
                "lunchCalories" to day.lunchCalories,
                "lunchCustomId" to day.lunchCustomId,
                "afternoonSnack" to day.afternoonSnack,
                "afternoonSnackCalories" to day.afternoonSnackCalories,
                "afternoonSnackCustomId" to day.afternoonSnackCustomId,
                "dinner" to day.dinner,
                "dinnerCalories" to day.dinnerCalories,
                "dinnerCustomId" to day.dinnerCustomId,
                "isCompleted" to day.isCompleted,
                "completedAt" to day.completedAt
            )
            usersCollection.document(userId).collection("diet_days")
                .document(day.dayNumber.toString()).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveAllDietDays(userId: String, days: List<DietDay>): Result<Unit> {
        return try {
            val batch = firestore.batch()
            days.forEach { day ->
                val ref = usersCollection.document(userId).collection("diet_days")
                    .document(day.dayNumber.toString())
                val data = hashMapOf(
                    "dayNumber" to day.dayNumber,
                    "breakfast" to day.breakfast,
                    "breakfastCalories" to day.breakfastCalories,
                    "breakfastCustomId" to day.breakfastCustomId,
                    "lunch" to day.lunch,
                    "lunchCalories" to day.lunchCalories,
                    "lunchCustomId" to day.lunchCustomId,
                    "afternoonSnack" to day.afternoonSnack,
                    "afternoonSnackCalories" to day.afternoonSnackCalories,
                    "afternoonSnackCustomId" to day.afternoonSnackCustomId,
                    "dinner" to day.dinner,
                    "dinnerCalories" to day.dinnerCalories,
                    "dinnerCustomId" to day.dinnerCustomId,
                    "isCompleted" to day.isCompleted,
                    "completedAt" to day.completedAt
                )
                batch.set(ref, data)
            }
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDietDays(userId: String): Result<List<DietDay>> {
        return try {
            val snapshot = usersCollection.document(userId).collection("diet_days").get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                val dayNumber = doc.getLong("dayNumber")?.toInt() ?: return@mapNotNull null
                DietDay(
                    dayNumber = dayNumber,
                    breakfast = doc.getString("breakfast") ?: "",
                    breakfastCalories = doc.getLong("breakfastCalories")?.toInt() ?: 0,
                    breakfastCustomId = doc.getLong("breakfastCustomId")?.toInt(),
                    lunch = doc.getString("lunch") ?: "",
                    lunchCalories = doc.getLong("lunchCalories")?.toInt() ?: 0,
                    lunchCustomId = doc.getLong("lunchCustomId")?.toInt(),
                    afternoonSnack = doc.getString("afternoonSnack") ?: "",
                    afternoonSnackCalories = doc.getLong("afternoonSnackCalories")?.toInt() ?: 0,
                    afternoonSnackCustomId = doc.getLong("afternoonSnackCustomId")?.toInt(),
                    dinner = doc.getString("dinner") ?: "",
                    dinnerCalories = doc.getLong("dinnerCalories")?.toInt() ?: 0,
                    dinnerCustomId = doc.getLong("dinnerCustomId")?.toInt(),
                    isCompleted = doc.getBoolean("isCompleted") ?: false,
                    completedAt = doc.getLong("completedAt")
                )
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Exercise Days ---
    suspend fun saveExerciseDay(userId: String, day: ExerciseDay): Result<Unit> {
        return try {
            val data = hashMapOf(
                "dayNumber" to day.dayNumber,
                "exerciseIds" to day.exerciseIds,
                "customExerciseIds" to day.customExerciseIds,
                "isCompleted" to day.isCompleted,
                "completedAt" to day.completedAt
            )
            usersCollection.document(userId).collection("exercise_days")
                .document(day.dayNumber.toString()).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveAllExerciseDays(userId: String, days: List<ExerciseDay>): Result<Unit> {
        return try {
            val batch = firestore.batch()
            days.forEach { day ->
                val ref = usersCollection.document(userId).collection("exercise_days")
                    .document(day.dayNumber.toString())
                val data = hashMapOf(
                    "dayNumber" to day.dayNumber,
                    "exerciseIds" to day.exerciseIds,
                    "customExerciseIds" to day.customExerciseIds,
                    "isCompleted" to day.isCompleted,
                    "completedAt" to day.completedAt
                )
                batch.set(ref, data)
            }
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getExerciseDays(userId: String): Result<List<ExerciseDay>> {
        return try {
            val snapshot = usersCollection.document(userId).collection("exercise_days").get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                val dayNumber = doc.getLong("dayNumber")?.toInt() ?: return@mapNotNull null
                ExerciseDay(
                    dayNumber = dayNumber,
                    exerciseIds = doc.getString("exerciseIds") ?: "",
                    customExerciseIds = doc.getString("customExerciseIds") ?: "",
                    isCompleted = doc.getBoolean("isCompleted") ?: false,
                    completedAt = doc.getLong("completedAt")
                )
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Custom Meals ---
    suspend fun saveCustomMeal(userId: String, meal: CustomMeal): Result<Unit> {
        return try {
            val data = hashMapOf(
                "id" to meal.id,
                "name" to meal.name,
                "category" to meal.category,
                "calories" to meal.calories,
                "description" to meal.description
            )
            usersCollection.document(userId).collection("custom_meals")
                .document(meal.id.toString()).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCustomMeal(userId: String, mealId: Int): Result<Unit> {
        return try {
            usersCollection.document(userId).collection("custom_meals")
                .document(mealId.toString()).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCustomMeals(userId: String): Result<List<CustomMeal>> {
        return try {
            val snapshot = usersCollection.document(userId).collection("custom_meals").get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                val id = doc.getLong("id")?.toInt() ?: return@mapNotNull null
                CustomMeal(
                    id = id,
                    name = doc.getString("name") ?: "",
                    category = doc.getString("category") ?: "",
                    calories = doc.getLong("calories")?.toInt() ?: 0,
                    description = doc.getString("description") ?: ""
                )
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Custom Exercises ---
    suspend fun saveCustomExercise(userId: String, exercise: CustomExercise): Result<Unit> {
        return try {
            val data = hashMapOf(
                "id" to exercise.id,
                "name" to exercise.name,
                "durationMinutes" to exercise.durationMinutes,
                "description" to exercise.description
            )
            usersCollection.document(userId).collection("custom_exercises")
                .document(exercise.id.toString()).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCustomExercise(userId: String, exerciseId: Int): Result<Unit> {
        return try {
            usersCollection.document(userId).collection("custom_exercises")
                .document(exerciseId.toString()).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCustomExercises(userId: String): Result<List<CustomExercise>> {
        return try {
            val snapshot = usersCollection.document(userId).collection("custom_exercises").get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                val id = doc.getLong("id")?.toInt() ?: return@mapNotNull null
                CustomExercise(
                    id = id,
                    name = doc.getString("name") ?: "",
                    durationMinutes = doc.getLong("durationMinutes")?.toInt() ?: 0,
                    description = doc.getString("description") ?: ""
                )
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Recipes ---
    suspend fun saveRecipe(userId: String, recipe: Recipe): Result<Unit> {
        return try {
            val data = hashMapOf(
                "id" to recipe.id,
                "title" to recipe.title,
                "category" to recipe.category,
                "ingredients" to recipe.ingredients,
                "steps" to recipe.steps,
                "calories" to recipe.calories,
                "prepTimeMinutes" to recipe.prepTimeMinutes,
                "imageDescription" to recipe.imageDescription,
                "imageUrl" to recipe.imageUrl,
                "isUserCreated" to recipe.isUserCreated
            )
            usersCollection.document(userId).collection("recipes")
                .document(recipe.id.toString()).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteRecipe(userId: String, recipeId: Int): Result<Unit> {
        return try {
            usersCollection.document(userId).collection("recipes")
                .document(recipeId.toString()).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRecipes(userId: String): Result<List<Recipe>> {
        return try {
            val snapshot = usersCollection.document(userId).collection("recipes").get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                val id = doc.getLong("id")?.toInt() ?: return@mapNotNull null
                Recipe(
                    id = id,
                    title = doc.getString("title") ?: "",
                    category = doc.getString("category") ?: "Breakfast",
                    ingredients = doc.getString("ingredients") ?: "",
                    steps = doc.getString("steps") ?: "",
                    calories = doc.getLong("calories")?.toInt() ?: 0,
                    prepTimeMinutes = doc.getLong("prepTimeMinutes")?.toInt() ?: 0,
                    imageDescription = doc.getString("imageDescription") ?: "",
                    imageUrl = doc.getString("imageUrl") ?: "",
                    isUserCreated = doc.getBoolean("isUserCreated") ?: false
                )
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Delete All User Data ---
    suspend fun deleteAllUserData(userId: String): Result<Unit> {
        return try {
            val batch = firestore.batch()

            // Helper to add deletes to batch
            suspend fun addCollectionDeletesToBatch(subcollectionName: String) {
                val snapshot = usersCollection.document(userId).collection(subcollectionName).get().await()
                snapshot.documents.forEach { doc ->
                    batch.delete(doc.reference)
                }
            }

            addCollectionDeletesToBatch("diet_days")
            addCollectionDeletesToBatch("exercise_days")
            addCollectionDeletesToBatch("custom_meals")
            addCollectionDeletesToBatch("custom_exercises")
            addCollectionDeletesToBatch("recipes")

            // Delete user doc itself
            batch.delete(usersCollection.document(userId))

            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
