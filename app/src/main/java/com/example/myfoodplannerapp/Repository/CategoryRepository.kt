package com.example.myfoodplannerapp.Repository

import com.example.myfoodplannerapp.Model.Category
import com.example.myfoodplannerapp.Network.retrofitHelper

class CategoryRepository {
    suspend fun getCategories(): List<Category> {
        return retrofitHelper.service.getAllCategories().categories
    }
}