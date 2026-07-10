package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.data.model.Recipe
import com.example.fittrackkk.ui.theme.FitTrackkkTheme
import com.example.fittrackkk.viewmodel.DiscoverViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreen(
    recipeId: Int?,
    viewModel: DiscoverViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Breakfast") }
    var calories by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var isEditMode by remember { mutableStateOf(false) }
    var originalRecipe by remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(recipeId) {
        if (recipeId != null) {
            val recipe = viewModel.getRecipeByIdOnce(recipeId)
            if (recipe != null) {
                originalRecipe = recipe
                isEditMode = true
                title = recipe.title
                imageUrl = recipe.imageUrl
                ingredients = recipe.ingredients
                steps = recipe.steps
                category = recipe.category
                calories = recipe.calories.toString()
                prepTime = recipe.prepTimeMinutes.toString()
            }
        }
    }

    AddEditRecipeScreenContent(
        title = title,
        imageUrl = imageUrl,
        ingredients = ingredients,
        steps = steps,
        category = category,
        calories = calories,
        prepTime = prepTime,
        isEditMode = isEditMode,
        onTitleChange = { title = it },
        onImageUrlChange = { imageUrl = it },
        onIngredientsChange = { ingredients = it },
        onStepsChange = { steps = it },
        onCategoryChange = { category = it },
        onCaloriesChange = { calories = it },
        onPrepTimeChange = { prepTime = it },
        onBack = onNavigateBack,
        onSave = {
            val cal = calories.toIntOrNull() ?: 0
            val time = prepTime.toIntOrNull() ?: 0
            if (isEditMode && originalRecipe != null) {
                viewModel.updateRecipe(
                    originalRecipe!!.copy(
                        title = title,
                        imageUrl = imageUrl,
                        ingredients = ingredients,
                        steps = steps,
                        category = category,
                        calories = cal,
                        prepTimeMinutes = time
                    )
                )
            } else {
                viewModel.addRecipe(
                    title = title,
                    category = category,
                    ingredients = ingredients,
                    steps = steps,
                    calories = cal,
                    prepTimeMinutes = time,
                    imageUrl = imageUrl
                )
            }
            onNavigateBack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreenContent(
    title: String,
    imageUrl: String,
    ingredients: String,
    steps: String,
    category: String,
    calories: String,
    prepTime: String,
    isEditMode: Boolean,
    onTitleChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onIngredientsChange: (String) -> Unit,
    onStepsChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onPrepTimeChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val scrollState = rememberScrollState()
    var dropdownExpanded by remember { mutableStateOf(false) }
    val categories = listOf("Breakfast", "Lunch", "Snack", "Dinner")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Recipe" else "Add Recipe", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Field
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Recipe Name") },
                modifier = Modifier.fillMaxWidth().testTag("recipeTitleInput"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Image URL Field
//            OutlinedTextField(
//                value = imageUrl,
//                onValueChange = onImageUrlChange,
//                label = { Text("Image URL") },
//                placeholder = { Text("https://example.com/food.jpg") },
//                modifier = Modifier.fillMaxWidth().testTag("recipeImageUrlInput"),
//                shape = RoundedCornerShape(12.dp),
//                singleLine = true
//            )

            // Category Selection Dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = {
                        IconButton(onClick = { dropdownExpanded = true }, modifier = Modifier.testTag("categoryDropdownIcon")) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Category")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().testTag("recipeCategoryInput"),
                    shape = RoundedCornerShape(12.dp)
                )
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                onCategoryChange(cat)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Calories & Prep Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = calories,
                    onValueChange = onCaloriesChange,
                    label = { Text("Calories (kcal)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f).testTag("recipeCaloriesInput"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = prepTime,
                    onValueChange = onPrepTimeChange,
                    label = { Text("Prep Time (mins)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f).testTag("recipePrepTimeInput"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Ingredients Field (Multiline)
            OutlinedTextField(
                value = ingredients,
                onValueChange = onIngredientsChange,
                label = { Text("Ingredients (One per line)") },
                modifier = Modifier.fillMaxWidth().height(120.dp).testTag("recipeIngredientsInput"),
                shape = RoundedCornerShape(12.dp),
                maxLines = 10
            )

            // Steps Field (Multiline)
            OutlinedTextField(
                value = steps,
                onValueChange = onStepsChange,
                label = { Text("Instructions (One step per line)") },
                modifier = Modifier.fillMaxWidth().height(150.dp).testTag("recipeStepsInput"),
                shape = RoundedCornerShape(12.dp),
                maxLines = 15
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = onSave,
                enabled = title.isNotBlank() && ingredients.isNotBlank() && steps.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(52.dp).testTag("saveRecipeButton"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = "Save")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Recipe", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditRecipeScreenPreview() {
    FitTrackkkTheme {
        AddEditRecipeScreenContent(
            title = "Mo:Mo",
            imageUrl = "https://example.com/momo.jpg",
            ingredients = "Minced Meat\nFlour Wrapper\nSpices",
            steps = "1. Wrap the filling\n2. Steam 10 mins",
            category = "Snack",
            calories = "450",
            prepTime = "25",
            isEditMode = false,
            onTitleChange = {},
            onImageUrlChange = {},
            onIngredientsChange = {},
            onStepsChange = {},
            onCategoryChange = {},
            onCaloriesChange = {},
            onPrepTimeChange = {},
            onBack = {},
            onSave = {}
        )
    }
}
