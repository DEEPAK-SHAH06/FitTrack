package com.example.fittrackkk.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object UserInfo : Screen("user_info")
    object Dashboard : Screen("dashboard")
    object DayDetail : Screen("day_detail/{dayNumber}") {
        fun createRoute(dayNumber: Int) = "day_detail/$dayNumber"
    }
    object ExerciseSession : Screen("exercise_session/{dayNumber}") {
        fun createRoute(dayNumber: Int) = "exercise_session/$dayNumber"
    }
    object RecipeDetail : Screen("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe_detail/$recipeId"
    }
    object ArticleDetail : Screen("article_detail/{articleId}") {
        fun createRoute(articleId: Int) = "article_detail/$articleId"
    }
}
