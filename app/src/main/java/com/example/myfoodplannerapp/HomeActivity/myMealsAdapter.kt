package com.example.myfoodplannerapp.HomeActivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfoodplannerapp.Model.meals
import com.example.myfoodplannerapp.R

class myMealsAdapter: RecyclerView.Adapter<myMealsAdapter.myViewHolder>() {
    private var data = listOf<meals>()
    private var onItemClickListener: ((meals) -> Unit)? = null

    fun setOnItemClickListener(listener: (meals) -> Unit) {
        onItemClickListener = listener
    }
    fun setMeals(meals: List<meals>) {
        this.data = meals
        notifyDataSetChanged()
    }
    class myViewHolder(row: View):RecyclerView.ViewHolder(row){
        val name = row.findViewById<TextView>(R.id.txtMeals)
        val img = row.findViewById<ImageView>(R.id.thumbnail)
    }
    var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        ctx= parent.context
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.meals_row,parent,false)
        return myViewHolder(layout)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text= data[position].strMeal
        ctx?.let { Glide.with(it).load(data[position].strMealThumb).into(holder.img) }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}