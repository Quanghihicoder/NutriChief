package com.example.nutrichief.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.RecipeIngredient
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class IngredientAdapter (
    private var recipeIngredientList: MutableList<RecipeIngredient>
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>(), Filterable {

    override fun getItemCount(): Int = recipeIngredientList.size

    // View holder class for holding view references
    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientImage: ImageView = view.findViewById(R.id.ingredient_image)
        val ingredientName: TextView = view.findViewById(R.id.ingredient_name)
        val ingredientShortDesc: TextView = view.findViewById(R.id.ingredient_shortDesc)
        val ingredientQuantity: TextView = view.findViewById(R.id.ingredient_qty)
    }

    private var searchIngredientList = ArrayList<RecipeIngredient>(recipeIngredientList)

    // Create view holder for recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_ingredient, parent, false) as View
        return IngredientViewHolder(view)
    }

    // Bind data to view holder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val menu = recipeIngredientList[position]
        holder.ingredientName.text = menu.ingredient.ingre_name
        holder.ingredientShortDesc.text = menu.ingredient.ingre_calo.toString() + "kcal, " + menu.ingredient.ingre_protein.toString() + "gr protein, " + menu.ingredient.ingre_fat.toString() + "gr fat, " + menu.ingredient.ingre_carb.toString() + "gr carb"
        holder.ingredientQuantity.text = menu.recipe_qty.toString() + "gr"
//        holder.ingredientImage.setImageResource(R.drawable.ramen)
//        Picasso.get().load(menu.ingredient.ingre_img).into(holder.ingredientImage)
        val imageUrl = menu.ingredient.ingre_img
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(holder.ingredientImage)
        } else {
            holder.ingredientImage.setImageResource(R.drawable.default_item_image)
        }
    }

    // Filter function
    override fun getFilter(): Filter {
        return searchFilter;
    }

    private val searchFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = java.util.ArrayList<RecipeIngredient>()
            if (constraint!!.isEmpty()) {
                filteredList.addAll(searchIngredientList)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()

                for (ingredient in searchIngredientList) {
                    if (ingredient.ingredient.ingre_name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(ingredient)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            recipeIngredientList.clear()
            recipeIngredientList.addAll(results!!.values as java.util.ArrayList<RecipeIngredient>)
            notifyDataSetChanged()
        }

    }

}