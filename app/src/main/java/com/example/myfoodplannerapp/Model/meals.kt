package com.example.myfoodplannerapp.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "meals_table")
@Parcelize
data class meals(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strArea: String,
    val strInstructions: String,
    val strYoutube: String?,
    val strIngredient1: String?,
    val strMeasure1: String?,
) : Parcelable

data class mealResponse(
    val meals: List<meals>
)