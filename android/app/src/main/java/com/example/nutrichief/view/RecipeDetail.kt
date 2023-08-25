package com.example.nutrichief.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.IngredientAdapter
import com.example.nutrichief.datamodels.Ingredient
import com.example.nutrichief.datamodels.RecipeIngredient

class RecipeDetail : AppCompatActivity() {
    private lateinit var ingredientRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        ingredientRecyclerView = findViewById(R.id.ingredients_recycler_view)
        ingredientRecyclerView.layoutManager = LinearLayoutManager(this)
        val dummy = mutableListOf<RecipeIngredient>(
            RecipeIngredient(
            1,
            Ingredient(1, "noodle", 100f, 100, 10f, 10f, 10f, "") ,
            100f),
            RecipeIngredient(
                1,
                Ingredient(2, "egg", 101f, 100, 10f, 10f, 10f, "") ,
                100f),
            RecipeIngredient(
                1,
                Ingredient(3, "pork", 102f, 100, 10f, 10f, 10f, "") ,
                100f),
        )
        ingredientRecyclerView.adapter = IngredientAdapter(dummy)
    }

    fun goBack(view: View) {}
}