package com.example.myfoodplannerapp.HomeActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodplannerapp.FavoriteMealsActivity.FavoriteMealsActivity
import com.example.myfoodplannerapp.MealDetailActivity.MealDetailActivity
import com.example.myfoodplannerapp.R
import com.example.myfoodplannerapp.SearchActivity.srchActivity


class GuestMainActivity : AppCompatActivity() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var mealViewModel: MealViewModel
    private lateinit var countryViewModel: CountryViewModel

    private lateinit var categoryAdapter: categoriesAdapter
    private lateinit var mealAdapter: myMealsAdapter
    private lateinit var countryAdapter: countriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_geust_main)
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.ctgRV)
        val mealRecyclerView = findViewById<RecyclerView>(R.id.mealRV)
        val countryRecyclerView = findViewById<RecyclerView>(R.id.cntryRV)
        // Set LayoutManagers for RecyclerViews
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mealRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        countryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        categoryAdapter = categoriesAdapter { category ->
            mealViewModel.getMealsByCategory(category.strCategory)
        }

        countryAdapter = countriesAdapter { country ->
            mealViewModel.getMealsByCountry(country.strArea)
        }

        mealAdapter = myMealsAdapter()

        categoryRecyclerView.adapter = categoryAdapter
        countryRecyclerView.adapter = countryAdapter
        mealRecyclerView.adapter = mealAdapter

        // Initialize ViewModels
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mealViewModel = ViewModelProvider(this).get(MealViewModel::class.java)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        // Observe Categories
        categoryViewModel.categoriesLiveData.observe(this, { categories ->
            categoryAdapter.setCategories(categories)
        })

        // Observe Countries
        countryViewModel.countriesLiveData.observe(this, { countries ->
            countryAdapter.setCountries(countries)
        })

        // Observe Meals
        mealViewModel.mealsLiveData.observe(this) { meals ->
            mealAdapter.setMeals(meals)
            mealAdapter.setOnItemClickListener { meal ->
                val intent = Intent(this, MealDetailActivity::class.java)
                intent.putExtra("meal", meal.idMeal)
                startActivity(intent)
            }
        }

        // Fetch Categories and Countries
        categoryViewModel.getCategories()
        countryViewModel.getCountries()
        mealViewModel.mealsLiveData.observe(this, { meals ->
            if (meals.isNotEmpty()) {
                mealAdapter.setMeals(meals)
            }
        })

    }
    fun search(view: View) {
        startActivity(Intent(this, srchActivity::class.java))
    }
    fun favorites(view: View) {
        Toast.makeText(this, "login first to see favorites", Toast.LENGTH_SHORT).show()

    }

    fun rndmMeal(view: View) {
        Toast.makeText(this, "login first to see meal of the day", Toast.LENGTH_SHORT).show()
    }
}