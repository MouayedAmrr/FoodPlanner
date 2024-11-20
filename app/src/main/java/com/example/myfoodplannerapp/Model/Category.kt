package com.example.myfoodplannerapp.Model

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)
data class CategoriesResponse(
    val categories: List<Category>
)