package com.example.myfoodplannerapp.SearchActivity

import com.example.myfoodplannerapp.MealDetailActivity.MealDetailActivity
import MealsViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodplannerapp.HomeActivity.myMealsAdapter
import com.example.myfoodplannerapp.R
import com.example.myfoodplannerapp.Repository.MealRepository

class srchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mealsAdapter: myMealsAdapter
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerCountry: Spinner
    private lateinit var editTextIngredient: EditText
    private lateinit var buttonSearch: Button

    private val mealsViewModel: MealsViewModel by viewModels {
        MealsViewModelFactory(MealRepository())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_results)
        spinnerCategory = findViewById(R.id.spinner_category)
        spinnerCountry = findViewById(R.id.spinner_country)
        editTextIngredient = findViewById(R.id.edittext_ingredient)
        buttonSearch = findViewById(R.id.btnSrch)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        mealsAdapter = myMealsAdapter()
        recyclerView.adapter = mealsAdapter

        // Fetch and populate category and country spinners
        setupSpinners()

        // Observe the ViewModel LiveData
        mealsViewModel.meals.observe(this, Observer { meals ->
            mealsAdapter.setMeals(meals)
            mealsAdapter.setOnItemClickListener { meal ->
                val intent = Intent(this, MealDetailActivity::class.java)
                intent.putExtra("meal", meal.idMeal)
                startActivity(intent)
            }
        })

        mealsViewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })

        // Handle the search button click
        buttonSearch.setOnClickListener {
            val selectedCategory = spinnerCategory.selectedItem.toString()
            val selectedCountry = spinnerCountry.selectedItem.toString()
            val ingredient = editTextIngredient.text.toString()

            when {
                selectedCategory != "Select Category" -> mealsViewModel.searchByCategory(selectedCategory)
                selectedCountry != "Select Country" -> mealsViewModel.searchByCountry(selectedCountry)
                ingredient.isNotBlank() -> mealsViewModel.searchByIngredient(ingredient)
                else -> Toast.makeText(this, "Please select a category, country, or enter an ingredient.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinners() {
        // Observe categories
        mealsViewModel.getCategories().observe(this, Observer { categories ->
            val categoryNames = listOf("Select Category") + categories.map { it.strCategory }
            val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = categoryAdapter
        })

        // Observe countries
        mealsViewModel.getCountries().observe(this, Observer { countries ->
            val countryNames = listOf("Select Country") + countries.map { it.strArea }
            val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryNames)
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountry.adapter = countryAdapter
        })
    }
}