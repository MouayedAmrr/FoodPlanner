package com.example.myfoodplannerapp.Model

data class MealDetails(
    val id: String,
    val name: String,
    val imageUrl: String,
    val origin: String,
    val steps: String,
    val videoUrl: String?,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String
)

fun meals.toMealDetails(): MealDetails {
    val ingredients = listOfNotNull(
        strIngredient1?.let { Ingredient(it, strMeasure1 ?: "") }
        // Add additional ingredients here
    )
    return MealDetails(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb,
        origin = strArea,
        steps = strInstructions,
        videoUrl = strYoutube,
        ingredients = ingredients
    )
}

