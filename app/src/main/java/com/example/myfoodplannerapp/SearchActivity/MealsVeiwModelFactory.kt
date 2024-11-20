package com.example.myfoodplannerapp.SearchActivity

import MealsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfoodplannerapp.Repository.MealRepository

class MealsViewModelFactory(private val repository: MealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
