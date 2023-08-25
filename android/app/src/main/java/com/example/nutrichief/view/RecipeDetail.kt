package com.example.nutrichief.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.IngredientAdapter
import com.example.nutrichief.datamodels.Ingredient
import com.example.nutrichief.datamodels.RecipeIngredient
import com.example.nutrichief.datamodels.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate

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

        val foodId = 1 // Replace with the desired food_id

        getRecipeData(foodId) { recipeIngredients ->
            recipeIngredients?.let {
                runOnUiThread {
                    adapter = IngredientAdapter(it as MutableList<RecipeIngredient>)
                    ingredientRecyclerView.layoutManager = LinearLayoutManager(this@RecipeDetail)
                    ingredientRecyclerView.adapter = adapter
                }
            } ?: run {
                // Handle the case when recipeIngredients is null (error occurred)
                Toast.makeText(this, "Failed to retrieve recipe ingredients", Toast.LENGTH_SHORT).show()
                Log.e("RecipeDetail", "Failed to retrieve recipe ingredients")
            }
        }
    }

    private fun getRecipeData(foodId: Int, callback: (List<RecipeIngredient>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()
                requestBody.put("food_id", foodId)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/recipe/ingre")
                    .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody.toString()))
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                if (!response.isSuccessful) {
                    throw IOException("Failed to retrieve recipe ingredients")
                }

                val responseBody = response.body?.string()
                val resultJson = JSONObject(responseBody ?: "")
                val status = resultJson.optInt("status", 0)

                if (status == 1) {
                    val data = resultJson.optJSONArray("data")

                    val recipeIngredients = mutableListOf<RecipeIngredient>()
                    for (i in 0 until data.length()) {
                        val ingredientJson = data.optJSONObject(i)
                        val ingredient = Ingredient(
                            ingredientJson.getInt("ingre_id"),
                            ingredientJson.getString("ingre_name"),
                            ingredientJson.getDouble("ingre_price").toFloat(),
                            ingredientJson.getInt("ingre_calo"),
                            ingredientJson.getDouble("ingre_fat").toFloat(),
                            ingredientJson.getDouble("ingre_protein").toFloat(),
                            ingredientJson.getDouble("ingre_carb").toFloat(),
                            ingredientJson.getString("ingre_img")
                        )
                        val recipeQty = ingredientJson.getDouble("recipe_qty").toFloat()

                        val recipeIngredient = RecipeIngredient(foodId, ingredient, recipeQty)
                        recipeIngredients.add(recipeIngredient)
                    }
                    callback(recipeIngredients)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                // Handle the error here
                callback(null)
                Log.e("RecipeDetail", "Failed to retrieve recipe ingredients: ${e.message}")
            }
        }
    }


    fun goBack(view: View) {}
}