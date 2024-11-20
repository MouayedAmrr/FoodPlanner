package com.example.myfoodplannerapp.HomeActivity

import com.example.myfoodplannerapp.MealDetailActivity.MealDetailActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodplannerapp.FavoriteMealsActivity.FavoriteMealsActivity
import com.example.myfoodplannerapp.R
import com.example.myfoodplannerapp.SearchActivity.srchActivity

//https://www.themealdb.com/api/json/v1/1/
class HomeActivity : AppCompatActivity() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var mealViewModel: MealViewModel
    private lateinit var countryViewModel: CountryViewModel

    private lateinit var categoryAdapter: categoriesAdapter
    private lateinit var mealAdapter: myMealsAdapter
    private lateinit var countryAdapter: countriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_home)
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.ctgRV)
        val mealRecyclerView = findViewById<RecyclerView>(R.id.mealRV)
        val countryRecyclerView = findViewById<RecyclerView>(R.id.cntryRV)

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


        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mealViewModel = ViewModelProvider(this).get(MealViewModel::class.java)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)


        categoryViewModel.categoriesLiveData.observe(this, { categories ->
            categoryAdapter.setCategories(categories)
        })


        countryViewModel.countriesLiveData.observe(this, { countries ->
            countryAdapter.setCountries(countries)
        })


        mealViewModel.mealsLiveData.observe(this) { meals ->
            mealAdapter.setMeals(meals)
            mealAdapter.setOnItemClickListener { meal ->
                val intent = Intent(this, MealDetailActivity::class.java)
                intent.putExtra("meal", meal.idMeal)
                startActivity(intent)
            }
        }

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
        startActivity(Intent(this, FavoriteMealsActivity::class.java))

    }

    fun rndmMeal(view: View) {
        startActivity(Intent(this, randomMealActivity::class.java))
    }
}