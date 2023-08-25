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
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class RecipeDetail : AppCompatActivity() {
    private lateinit var ingredientRecyclerView: RecyclerView
    private lateinit var adapter: IngredientAdapter
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        ingredientRecyclerView = findViewById(R.id.ingredients_recycler_view)
//        ingredientRecyclerView.layoutManager = LinearLayoutManager(this)
//        val dummy = mutableListOf<RecipeIngredient>(
//            RecipeIngredient(
//            1,
//            Ingredient(1, "noodle", 100f, 100, 10f, 10f, 10f, "") ,
//            100f),
//            RecipeIngredient(
//                1,
//                Ingredient(2, "egg", 101f, 100, 10f, 10f, 10f, "") ,
//                100f),
//            RecipeIngredient(
//                1,
//                Ingredient(3, "pork", 102f, 100, 10f, 10f, 10f, "") ,
//                100f),
//        )
//        ingredientRecyclerView.adapter = IngredientAdapter(dummy)

        val foodId = 123 // Replace with the desired food_id
        val requestBody = FormBody.Builder()
            .add("food_id", foodId.toString())
            .build()

        val request = Request.Builder()
            .url("http://10.0.2.2:8001/apis/recipe/ingre") // Replace with your actual API URL
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val ingredientList: List<RecipeIngredient> = parseJson(responseBody)

                runOnUiThread {
                    adapter = IngredientAdapter(ingredientList as MutableList<RecipeIngredient>)
                    ingredientRecyclerView.adapter = adapter
                    ingredientRecyclerView.layoutManager = LinearLayoutManager(this@RecipeDetail)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Handle failure
            }
        })
    }

    private fun parseJson(json: String?): List<RecipeIngredient> {
        val gson = Gson()
        return gson.fromJson(json, Array<RecipeIngredient>::class.java).toList()
    }

    fun goBack(view: View) {}
}