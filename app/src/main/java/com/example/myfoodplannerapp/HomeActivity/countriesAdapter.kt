package com.example.myfoodplannerapp.HomeActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfoodplannerapp.Model.Country
import com.example.myfoodplannerapp.R


class countriesAdapter(private val onCountryClick: (Country) -> Unit):RecyclerView.Adapter<countriesAdapter.myViewHolder>() {
    private var data = listOf<Country>()

    fun setCountries(countries: List<Country>) {
        this.data = countries
        notifyDataSetChanged()
    }
    class myViewHolder(row: View):RecyclerView.ViewHolder(row){
        val cntryName = row.findViewById<TextView>(R.id.txtcntry)
    }
    var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        ctx= parent.context
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cntry_row,parent,false)
        return myViewHolder(layout)
    }
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.cntryName.text= data[position].strArea
        holder.itemView.setOnClickListener {
            onCountryClick(data[position])
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}