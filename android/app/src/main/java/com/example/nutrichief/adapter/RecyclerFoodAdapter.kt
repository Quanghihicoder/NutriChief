package com.example.nutrichief.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.Food
import java.util.*
import kotlin.collections.ArrayList

class RecyclerFoodAdapter(private var foodList: MutableList<Food>)
    : RecyclerView.Adapter<RecyclerFoodAdapter.FoodItemViewHolder>(), Filterable {

    private var filteredFoodList = ArrayList<Food>(foodList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_food, parent, false,) as View
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val food = foodList[position]
        holder.imageItemFood.setImageResource(R.drawable.ramen)
        holder.textItemFoodName.text = food.foodName
        holder.textItemFoodShortDesc.text = food.foodDesc
    }

    override fun getItemCount(): Int = foodList.size

    inner class FoodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItemFood: ImageView = itemView.findViewById(R.id.image_item_food)
        val textItemFoodName: TextView = itemView.findViewById(R.id.text_item_food_name)
        val textItemFoodShortDesc: TextView = itemView.findViewById(R.id.text_item_food_short_desc)
    }

    override fun getFilter(): Filter {
        return searchFilter;
    }

    fun filterList(filteredList: MutableList<Food>) {
        foodList = filteredList
        notifyDataSetChanged()
    }

    private val searchFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Food>()
            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(filteredFoodList)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()

                for (food in filteredFoodList) {
                    if (food.foodName.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(food)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.values is ArrayList<*>) {
                foodList = results.values as ArrayList<Food>
                notifyDataSetChanged()
            }
        }
    }
}