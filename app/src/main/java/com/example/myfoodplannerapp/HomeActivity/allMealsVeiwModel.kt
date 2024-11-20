package com.example.myfoodplannerapp.HomeActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfoodplannerapp.Model.Category
import com.example.myfoodplannerapp.Model.Country
import com.example.myfoodplannerapp.Model.meals
import com.example.myfoodplannerapp.Network.retrofitHelper
import com.example.myfoodplannerapp.Repository.CategoryRepository
import com.example.myfoodplannerapp.Repository.CountryRepository
import com.example.myfoodplannerapp.Repository.MealRepository
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val repository = CategoryRepository()
    val categoriesLiveData: MutableLiveData<List<Category>> = MutableLiveData()

    fun getCategories() {
        viewModelScope.launch {
            val categories = repository.getCategories()
            categoriesLiveData.postValue(categories)
        }
    }
}

class MealViewModel : ViewModel() {
    private val repository = MealRepository()
    val mealsLiveData: MutableLiveData<List<meals>> = MutableLiveData()

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            val meals = repository.getMealsByCategory(category)
            mealsLiveData.postValue(meals)
        }
    }
    fun getMealsByCountry(country: String) {
        viewModelScope.launch {
            val meals = repository.getMealsByCountry(country)
            mealsLiveData.postValue(meals)
        }
    }
}


class CountryViewModel : ViewModel() {
    private val repository = CountryRepository()
    val countriesLiveData: MutableLiveData<List<Country>> = MutableLiveData()

    fun getCountries() {
        viewModelScope.launch {
            val countries = repository.getCountries()
            countriesLiveData.postValue(countries)
        }
    }
}





