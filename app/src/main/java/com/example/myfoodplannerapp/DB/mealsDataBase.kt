package com.example.productsapp.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfoodplannerapp.Model.meals

@Database(entities = arrayOf(meals::class), version = 1)
abstract class mealsDataBase : RoomDatabase(){

    abstract fun getMealsDao(): ProductDao
        companion object {
            @Volatile
            private var DB: mealsDataBase? = null
            fun getInstance(ctx: Context): mealsDataBase {
                return DB ?: synchronized(this) {
                    val tempInstance =
                        Room.databaseBuilder(ctx, mealsDataBase::class.java, "meals_db").build()
                DB=tempInstance
                tempInstance
                }
            }

    }

}