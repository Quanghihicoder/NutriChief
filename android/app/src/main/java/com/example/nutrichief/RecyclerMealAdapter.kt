package com.example.nutrichief

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.model.Meal


class RecyclerMealAdapter(
    private var mealList: List<Meal>,
    private var listener: onMealCheckedChange
) : RecyclerView.Adapter<RecyclerMealAdapter.MealItemViewHolder>() {

    interface onMealCheckedChange {
        fun onMealCheckChange(meal: Meal, mealCheck: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_meal, parent, false) as View
        return MealItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealItemViewHolder, position: Int) {
        val meal = mealList[position]
        holder.cboxIsConsumed.text = meal.mealType
        holder.cboxIsConsumed.setOnCheckedChangeListener { buttonView, isChecked ->
            val mealChecked = if (isChecked) 1 else 0
            listener.onMealCheckChange(meal, mealChecked)
        }

        holder.recyclerViewMealItem.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recyclerViewMealItem.adapter = RecyclerFoodAdapter(meal.foodList)

    }

    override fun getItemCount(): Int = mealList.size

    inner class MealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cboxIsConsumed: CheckBox = itemView.findViewById(R.id.cbox_is_consumed)
        val recyclerViewMealItem: RecyclerView = itemView.findViewById(R.id.recycler_view_food)
    }
}