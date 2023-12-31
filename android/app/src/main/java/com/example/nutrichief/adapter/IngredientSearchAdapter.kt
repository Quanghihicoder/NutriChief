package com.example.nutrichief.adapter

import android.annotation.SuppressLint
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
import com.example.nutrichief.datamodels.Ingredient
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class IngredientSearchAdapter (
    private var ingredientList: MutableList<Ingredient>,
) : RecyclerView.Adapter<IngredientSearchAdapter.IngredientViewHolder>(), Filterable {

    private var filteredIngredientList = ArrayList<Ingredient>(ingredientList)

    override fun getItemCount(): Int = ingredientList.size

    // Filter function
    override fun getFilter(): Filter {
        return searchFilter;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: MutableList<Ingredient>) {
        ingredientList = filteredList
        notifyDataSetChanged()
    }

    private val searchFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Ingredient>()
            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(filteredIngredientList)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()

                for (ingredient in filteredIngredientList) {
                    if (ingredient.ingre_name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(ingredient)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            Log.d("IngredientSearchAdapter", "Performing filtering with constraint: $constraint")
            ingredientList.clear()
            ingredientList.addAll(results?.values as ArrayList<Ingredient>)
            notifyDataSetChanged()
        }
    }

    // View holder class for holding view references
    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientImage: ImageView = view.findViewById(R.id.ingredient_image)
        val ingredientName: TextView = view.findViewById(R.id.ingredient_name)
        val ingredientShortDesc: TextView = view.findViewById(R.id.ingredient_shortDesc)
    }

    // Create view holder for recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_ingredient, parent, false) as View
        return IngredientViewHolder(view)
    }

    // Bind data to view holder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val menu = ingredientList[position]
        holder.ingredientName.text = menu.ingre_name
        holder.ingredientShortDesc.text = menu.ingre_calo.toString() + "kcal, " + menu.ingre_protein.toString() + "gr protein, " + menu.ingre_fat.toString() + "gr fat, " + menu.ingre_carb.toString() + "gr carb"
        val imageUrl = menu.ingre_img
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(holder.ingredientImage)
        } else {
            holder.ingredientImage.setImageResource(R.drawable.default_item_image)
        }
    }
}