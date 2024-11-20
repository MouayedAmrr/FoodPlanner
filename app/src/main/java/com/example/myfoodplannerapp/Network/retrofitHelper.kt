package com.example.myfoodplannerapp.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ingredientService::class.java)
}