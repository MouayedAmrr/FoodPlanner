package com.example.myfoodplannerapp.Repository

import com.example.myfoodplannerapp.Model.Category
import com.example.myfoodplannerapp.Model.Country
import com.example.myfoodplannerapp.Model.meals
import com.example.myfoodplannerapp.Network.retrofitHelper
import org.json.JSONObject

class MealRepository {
    suspend fun getMealsByCategory(category: String): List<meals> {
        return try {
            retrofitHelper.service.getMealsByCategory(category).meals
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getMealsByCountry(country: String): List<meals> {
        return try {
            retrofitHelper.service.getMealsByCountry(country).meals
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getMealsByIngredient(ingredient: String): List<meals> {
        return try {
            retrofitHelper.service.getMealsByIngredient(ingredient).meals
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getCategories(): List<Category> {
        return retrofitHelper.service.getAllCategories().categories    }

    suspend fun getCountries(): List<Country> {
        return retrofitHelper.service.getCountries().meals
    }
}