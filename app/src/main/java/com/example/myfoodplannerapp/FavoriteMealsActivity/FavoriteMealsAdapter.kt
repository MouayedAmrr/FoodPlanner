package com.example.myfoodplannerapp.FavoriteMealsActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfoodplannerapp.Model.meals
import com.example.myfoodplannerapp.R

class FavoriteMealsAdapter(
    private val meals: List<meals>,
    private val onRemoveClick: (meals) -> Unit,
    private val onClick: (meals) -> Unit
) : RecyclerView.Adapter<FavoriteMealsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImage)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealName)
        private val removeButton: Button = itemView.findViewById(R.id.removeButton)

        fun bind(meal: meals) {
            mealNameTextView.text = meal.strMeal
            Glide.with(itemView.context).load(meal.strMealThumb).into(mealImageView)
            removeButton.setOnClickListener { onRemoveClick(meal) }
            itemView.setOnClickListener { onClick(meal) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_meal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount() = meals.size
}
