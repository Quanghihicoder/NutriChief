package com.example.nutrichief

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.model.Meal

class RecyclerMealAdapter(private var mealList: List<Meal>): RecyclerView.Adapter<RecyclerMealAdapter.MealItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_meal, parent, false,) as View
        return MealItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealItemViewHolder, position: Int) {
        val meal = mealList[position]
        holder.textMealType.text = meal.mealType

        holder.recyclerViewMealItem.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recyclerViewMealItem.adapter = RecyclerFoodAdapter(meal.foodList)
    }

    override fun getItemCount(): Int = mealList.size

    inner class MealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textMealType: TextView = itemView.findViewById(R.id.text_meal_type)
        val recyclerViewMealItem: RecyclerView = itemView.findViewById(R.id.recycler_view_food)
    }
}