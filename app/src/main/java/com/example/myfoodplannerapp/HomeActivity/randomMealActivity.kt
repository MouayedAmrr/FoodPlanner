package com.example.myfoodplannerapp.HomeActivity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myfoodplannerapp.MealDetailActivity.MealDetailActivity
import com.example.myfoodplannerapp.Network.ingredientService
import com.example.myfoodplannerapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class randomMealActivity : AppCompatActivity() {
    private lateinit var mealImageView: ImageView
    private lateinit var mealNameTextView: TextView
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_random_meal)
        mealImageView = findViewById(R.id.thumbnailRnd)
        mealNameTextView = findViewById(R.id.txtMealRnd)

        fetchMealOfTheDay()
    }

    private fun fetchMealOfTheDay() {
        coroutineScope.launch {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ingredientService::class.java)
                val mealResponse = withContext(Dispatchers.IO) { service.getMealOfTheDay() }
                val meal = mealResponse.meals.firstOrNull()

                if (meal != null) {
                    Glide.with(this@randomMealActivity)
                        .load(meal.strMealThumb)
                        .into(mealImageView)
                    mealNameTextView.text = meal.strMeal
                    }

                    mealImageView.setOnClickListener {
                        val intent = Intent(this@randomMealActivity, MealDetailActivity::class.java)
                        if (meal != null) {
                            intent.putExtra("meal", meal.idMeal)
                        }
                        startActivity(intent)
                    }
        }
    }
}
