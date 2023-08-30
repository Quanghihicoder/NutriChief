package com.example.nutrichief.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.Food
import com.example.nutrichief.datamodels.MenuItem
import java.util.*
import kotlin.collections.ArrayList

class FoodOrderAdapter(private var foodList: MutableList<MenuItem>,
                       private val listener: OnItemClickListener
)
    : RecyclerView.Adapter<FoodOrderAdapter.FoodItemViewHolder>(), Filterable {

    private var filteredFoodList = ArrayList<MenuItem>(foodList)
    private var quantity: Int? = null

    // Interface for defining click listener callbacks
    interface OnItemClickListener {
        fun onFoodClick(item: MenuItem)
        fun onPlusBtnClick(item: MenuItem)
        fun onMinusBtnClick(item: MenuItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_food, parent, false,) as View
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val food = foodList[position]
        holder.imageItemFood.setImageResource(R.drawable.ramen)
        holder.textItemFoodName.text = food.food_name
        holder.textItemFoodShortDesc.text = food.food_desc
        holder.itemPrice.text = "$" + food.food_price.toString()

        holder.itemView.setOnClickListener{
            listener.onFoodClick(food)
        }

        holder.itemPlus.setOnClickListener {
            val number = food.food_quantity
            holder.itemQuantity.text = (number + 1).toString()
            listener.onPlusBtnClick(food)
        }

        holder.itemMinus.setOnClickListener {
            val number = food.food_quantity
            if (number > 0) {
                holder.itemQuantity.text = (number - 1).toString()
                listener.onMinusBtnClick(food)
            }
        }
    }

    override fun getItemCount(): Int = foodList.size

    inner class FoodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItemFood: ImageView = itemView.findViewById(R.id.image_item_food)
        val textItemFoodName: TextView = itemView.findViewById(R.id.text_item_food_name)
        val textItemFoodShortDesc: TextView = itemView.findViewById(R.id.text_item_food_short_desc)
        val itemPlus: ImageView = itemView.findViewById(R.id.item_plus)
        val itemMinus: ImageView = itemView.findViewById(R.id.item_minus)
        val itemQuantity: TextView = itemView.findViewById(R.id.item_quantity)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
    }

    override fun getFilter(): Filter {
        return searchFilter;
    }

    fun filterList(filteredList: MutableList<MenuItem>) {
        foodList = filteredList
        notifyDataSetChanged()
    }

    private val searchFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<MenuItem>()
            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(filteredFoodList)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()

                for (food in filteredFoodList) {
                    if (food.food_name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
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
                foodList = results.values as ArrayList<MenuItem>
                notifyDataSetChanged()
            }
        }
    }
}