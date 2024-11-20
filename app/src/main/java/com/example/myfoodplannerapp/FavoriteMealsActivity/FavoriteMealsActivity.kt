package com.example.myfoodplannerapp.FavoriteMealsActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodplannerapp.MealDetailActivity.MealDetailActivity
import com.example.myfoodplannerapp.R
import com.example.productsapp.DB.mealsDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMealsActivity : AppCompatActivity() {

    private lateinit var favoriteMealsRecyclerView: RecyclerView
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val db by lazy { mealsDataBase.getInstance(this).getMealsDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_meals)

        favoriteMealsRecyclerView = findViewById(R.id.favoriteMealsRecyclerView)
        favoriteMealsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        loadFavoriteMeals()
    }

    private fun loadFavoriteMeals() {
        coroutineScope.launch {
            val meals = withContext(Dispatchers.IO) { db.getAllFavoriteMeals() }
            favoriteMealsRecyclerView.adapter = FavoriteMealsAdapter(
                meals,
                onRemoveClick = { meal ->
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            db.deleteMeal(meal.idMeal)
                        }
                        loadFavoriteMeals()
                        Toast.makeText(this@FavoriteMealsActivity, "meal removed", Toast.LENGTH_SHORT).show()
                    }
                },
                onClick = { meal ->
                    val intent = Intent(this@FavoriteMealsActivity, MealDetailActivity::class.java)
                    intent.putExtra("meal", meal.idMeal)
                    startActivity(intent)
                }
            )
        }
    }
}
