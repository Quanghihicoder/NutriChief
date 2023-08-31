package com.example.nutrichief.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.IngredientAdapter
import com.example.nutrichief.datamodels.Food
import com.example.nutrichief.datamodels.Ingredient
import com.example.nutrichief.datamodels.RecipeIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var ingredientRecyclerView: RecyclerView
    private lateinit var adapter: IngredientAdapter

    private var recipeCalories: Float = 0.0f
    private var recipeProtein: Float = 0.0f
    private var recipeFat: Float = 0.0f
    private var recipeCarb: Float = 0.0f

    private var food_name: String = ""
    private var food_desc: String = ""
    private var food_ctime: Int = 0
    private var food_ptime: Int = 0

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        ingredientRecyclerView = findViewById(R.id.ingredients_recycler_view)

//        val foodId = 2 // Replace with the desired food_id
        val foodId = intent.getIntExtra("food_id", 1)
        Log.e("food_id intent", foodId.toString())

        val kcalTV = findViewById<TextView>(R.id.caloriesValue)
        val proteinTV = findViewById<TextView>(R.id.proteinValue)
        val fatTV = findViewById<TextView>(R.id.fatValue)
        val carbTV = findViewById<TextView>(R.id.carbValue)

        getRecipeData(foodId) { recipeIngredients ->
            recipeIngredients?.let {
                runOnUiThread {
                    adapter = IngredientAdapter(it as MutableList<RecipeIngredient>)
                    ingredientRecyclerView.layoutManager = LinearLayoutManager(this@RecipeDetailActivity)
                    ingredientRecyclerView.adapter = adapter

                    kcalTV.text = recipeCalories.toString() + " kcal"
                    proteinTV.text = recipeProtein.toString() + "g"
                    fatTV.text = recipeFat.toString() + "g"
                    carbTV.text = recipeCarb.toString() + "g"
                }
            } ?: run {
                // Handle the case when recipeIngredients is null (error occurred)
                Toast.makeText(this, "Failed to retrieve recipe ingredients", Toast.LENGTH_SHORT).show()
                Log.e("RecipeDetail", "Failed to retrieve recipe ingredients")
            }
        }

        getFoodData(foodId) { food ->
            food?.let {
                runOnUiThread {
                    val pTimeTV = findViewById<TextView>(R.id.pTimeTV)
                    val cTimeTV = findViewById<TextView>(R.id.cTimeTV)
                    val foodNameTV = findViewById<TextView>(R.id.foodName)

                    pTimeTV.text = food_ptime.toString() + "mins to prepare"
                    cTimeTV.text = food_ctime.toString() + "mins to cook"
                    foodNameTV.text = food_name
                }
            } ?: run {
                // Handle the case when recipeIngredients is null (error occurred)
                Toast.makeText(this, "Failed to retrieve food", Toast.LENGTH_SHORT)
                    .show()
                Log.e("RecipeDetail", "Failed to retrieve food")
            }
        }

        val startCookingButton = findViewById<Button>(R.id.startCookingButton)
        startCookingButton.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            intent.putExtra("food_id", foodId)
            startActivity(intent)
        }

    }

    private fun getRecipeData(foodId: Int, callback: (List<RecipeIngredient>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()
                requestBody.put("food_id", foodId)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/food/recipe/detail")
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
                        val recipeTitle = ingredientJson.getString("recipe_title")
                        val recipeDesc = ingredientJson.getString("recipe_desc")
                        val recipePrice = ingredientJson.getDouble("recipe_price").toFloat()
                        recipeCalories = ingredientJson.getDouble("recipe_calories").toFloat()
                        recipeProtein = ingredientJson.getDouble("recipe_protein").toFloat()
                        recipeFat = ingredientJson.getDouble("recipe_fat").toFloat()
                        recipeCarb = ingredientJson.getDouble("recipe_carb").toFloat()
                        val recipeVideo = ingredientJson.getString("media_url")

                        val recipeIngredient = RecipeIngredient(foodId, ingredient, recipeQty, recipeTitle, recipeDesc, recipePrice, recipeCalories, recipeCarb, recipeFat, recipeProtein, recipeVideo)
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

    private fun getFoodData(foodId: Int, callback: (List<Food>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/food/$foodId")
                    .get()
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                if (!response.isSuccessful) {
                    throw IOException("Failed to retrieve food")
                }

                val responseBody = response.body?.string()
                val resultJson = JSONObject(responseBody ?: "")
                val status = resultJson.optInt("status", 0)

                if (status == 1) {
                    val data = resultJson.optJSONArray("data")

                    val foods = mutableListOf<Food>()
                    for (i in 0 until data.length()) {
                        val foodJson = data.optJSONObject(i)

                        food_name = foodJson.getString("food_name")
                        food_desc = foodJson.getString("food_desc")
                        food_ctime = foodJson.getInt("food_ctime")
                        food_ptime = foodJson.getInt("food_ptime")
                        val food_img = foodJson.getString("food_img")

                        val food = Food(foodId, food_name, food_desc, food_ctime, food_ptime, food_img)
                        foods.add(food)
                    }
                    callback(foods)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                // Handle the error here
                callback(null)
                Log.e("RecipeDetail", "Failed to retrieve food: ${e.message}")
            }
        }
    }

    fun goBack(view: View) { onBackPressedDispatcher.onBackPressed() }
}