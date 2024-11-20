package com.example.myfoodplannerapp.HomeActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfoodplannerapp.Model.Category
import com.example.myfoodplannerapp.R

class categoriesAdapter(private val onCategoryClick: (Category) -> Unit): RecyclerView.Adapter<categoriesAdapter.myViewHolder>() {
    private var data = listOf<Category>()

    fun setCategories(categories: List<Category>) {
        this.data = categories
        notifyDataSetChanged()
    }
    class myViewHolder(row: View):RecyclerView.ViewHolder(row){
        val Ctgname = row.findViewById<TextView>(R.id.txtctg)
        val ctgImg = row.findViewById<ImageView>(R.id.thumbnail)
    }
    var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        ctx= parent.context
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.catg_row,parent,false)
        return myViewHolder(layout)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.Ctgname.text= data[position].strCategory
        ctx?.let { Glide.with(it).load(data[position].strCategoryThumb).into(holder.ctgImg) }
        holder.itemView.setOnClickListener {
            onCategoryClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}