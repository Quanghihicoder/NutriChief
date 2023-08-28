package com.example.nutrichief

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.model.Food

class RecyclerFoodAdapter(private var foodList: List<Food>): RecyclerView.Adapter<RecyclerFoodAdapter.FoodItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_food, parent, false,) as View
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val food = foodList[position]
        holder.imageItemFood.setImageResource(R.drawable.img_ramen)
        holder.textItemFoodName.text = food.foodName
        holder.textItemFoodShortDesc.text = food.foodDesc
    }

    override fun getItemCount(): Int = foodList.size

    inner class FoodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItemFood: ImageView = itemView.findViewById(R.id.image_item_food)
        val textItemFoodName: TextView = itemView.findViewById(R.id.text_item_food_name)
        val textItemFoodShortDesc: TextView = itemView.findViewById(R.id.text_item_food_short_desc)
    }
}