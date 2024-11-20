package com.example.myfoodplannerapp.Repository

import com.example.myfoodplannerapp.Model.Country
import com.example.myfoodplannerapp.Network.retrofitHelper


class CountryRepository {
    suspend fun getCountries() : List<Country>{
        return retrofitHelper.service.getCountries().meals
    }
}