package com.example.myfoodplannerapp.MealDetailActivity

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodplannerapp.Model.MealDetails
import com.example.myfoodplannerapp.Model.mealResponse
import com.example.myfoodplannerapp.Model.meals
import com.example.myfoodplannerapp.Model.toMealDetails
import com.example.myfoodplannerapp.Network.retrofitHelper
import com.example.myfoodplannerapp.R
import com.example.productsapp.DB.mealsDataBase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealDetailActivity : AppCompatActivity() {

    private lateinit var mealImageView: ImageView
    private lateinit var mealNameTextView: TextView
    private lateinit var mealOriginTextView: TextView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var mealStepsTextView: TextView
    private lateinit var mealVideoView: VideoView
    private lateinit var favoriteButton: Button

    private val apiService = retrofitHelper.service
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val db by lazy { mealsDataBase.getInstance(this).getMealsDao() }
    private var currentMealId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_detail)

        // Initialize views
        mealImageView = findViewById(R.id.mealImage)
        mealNameTextView = findViewById(R.id.mealName)
        mealOriginTextView = findViewById(R.id.mealOrigin)
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView)
        mealStepsTextView = findViewById(R.id.mealSteps)
        mealVideoView = findViewById(R.id.mealVideoView)
        favoriteButton = findViewById(R.id.favoriteButton)

        // Get meal ID from intent
        val currentMealId = intent.getStringExtra("meal")

        // Fetch meal details
        currentMealId?.let {
            fetchMealDetails(it)
        }

        favoriteButton.setOnClickListener {
            currentMealId?.let { mealId ->
                coroutineScope.launch {
                    if (isFavorite(mealId)) {
                        removeFromFavorites(mealId)
                    } else {
                        addToFavorites(mealId)
                    }
                    updateFavoriteButton()
                }
            }
        }
    }


    private fun fetchMealDetails(mealId: String) {
        coroutineScope.launch {
            val mealDetails = withContext(Dispatchers.IO) {
                try {
                    val response: retrofit2.Response<mealResponse> = apiService.getMealDetails(mealId)
                    if (response.isSuccessful) {
                        response.body()?.meals?.firstOrNull()?.toMealDetails()
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }
            mealDetails?.let {
                updateUI(it)
            }
        }
    }

    private fun updateUI(mealDetails: MealDetails) {
        mealNameTextView.text = mealDetails.name
        mealOriginTextView.text = mealDetails.origin
        mealStepsTextView.text = mealDetails.steps
        Picasso.get().load(mealDetails.imageUrl).into(mealImageView)

        val adapter = IngredientsAdapter(mealDetails.ingredients)
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        ingredientsRecyclerView.adapter = adapter

        mealVideoView.setVideoURI(Uri.parse("android.resource://${packageName}/${R.raw.meal_video}"))
        mealVideoView.start()
        updateFavoriteButton()
    }
        // Update favorite button state
        private suspend fun isFavorite(mealId: String): Boolean {
            val meal = db.getAllFavoriteMeals().find { it.idMeal == mealId }
            return meal != null
        }

        private suspend fun addToFavorites(mealId: String) {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            val meal = fetchMealFromApi(mealId) ?: return
            db.insertMeal(meal)
        }

        private suspend fun removeFromFavorites(mealId: String) {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
            db.deleteMeal(mealId)
        }

        private suspend fun fetchMealFromApi(mealId: String): meals? {
            val response = apiService.getMealDetails(mealId)
            return if (response.isSuccessful) {
                response.body()?.meals?.firstOrNull()?.let { meal ->
                    meals(
                        idMeal = meal.idMeal,
                        strMeal = meal.strMeal,
                        strMealThumb = meal.strMealThumb,
                        strArea = meal.strArea ?: "Unknown",
                        strInstructions = meal.strInstructions ?: "",
                        strIngredient1 = meal.strIngredient1 ?:"",
                        strYoutube = meal.strYoutube?:"",
                        strMeasure1 = meal.strMeasure1
                    )
                }
            } else {
                null
            }
        }

        private fun updateFavoriteButton() {
            currentMealId?.let { mealId ->
                coroutineScope.launch {
                    favoriteButton.text = if (isFavorite(mealId)) "Remove from Favorites" else "Add to Favorites"
                }
            }
        }
    }


