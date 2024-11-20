package com.example.myfoodplannerapp.Model

data class Country(
    val strArea: String
)

data class CountriesResponse(
    val meals: List<Country>
)