package com.example.myfoodplannerapp.Network

import com.example.myfoodplannerapp.Model.CategoriesResponse
import com.example.myfoodplannerapp.Model.CountriesResponse
import com.example.myfoodplannerapp.Model.mealResponse
import com.example.myfoodplannerapp.Model.meals
import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ingredientService {
    @GET("categories.php")
    suspend fun getAllCategories(): CategoriesResponse

    @GET("list.php?a=list")
    suspend fun getCountries(): CountriesResponse

    @GET("random.php")
    suspend fun getMealOfTheDay(): mealResponse

    @GET("filter.php?")
    suspend fun getMealsByCategory(@Query("c") category: String): mealResponse

    @GET("filter.php?")
    suspend fun getMealsByCountry(@Query("a") country: String): mealResponse

    @GET("filter.php?")
    suspend fun getMealsByIngredient(@Query("i") ingredient: String): mealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): retrofit2.Response<mealResponse>


}