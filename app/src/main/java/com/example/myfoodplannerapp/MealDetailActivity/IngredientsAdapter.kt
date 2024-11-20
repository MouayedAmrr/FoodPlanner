package com.example.myfoodplannerapp.MealDetailActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.myfoodplannerapp.Model.Ingredient
import com.example.myfoodplannerapp.R


class IngredientsAdapter(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.ingredientName.text = ingredient.name
        holder.ingredientMeasure.text = ingredient.measure
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView = view.findViewById(R.id.ingredientName)
        val ingredientMeasure: TextView = view.findViewById(R.id.ingredientMeasure)
    }
}
