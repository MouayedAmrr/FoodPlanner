package com.example.productsapp.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfoodplannerapp.Model.Category
import com.example.myfoodplannerapp.Model.meals


@Dao
interface ProductDao {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertMeal(meal: meals):Long

    @Query("DELETE FROM meals_table WHERE idMeal = :mealId")
    suspend fun deleteMeal(mealId: String)


    @Query("SELECT * FROM meals_table WHERE idMeal = :idMeal")
    suspend fun getMealById(idMeal: String): meals?


    @Query("SELECT * FROM meals_table")
    suspend fun getAllFavoriteMeals() : List<meals>
}