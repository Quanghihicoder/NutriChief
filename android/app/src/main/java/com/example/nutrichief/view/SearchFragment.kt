package com.example.nutrichief.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.IngredientSearchAdapter
import com.example.nutrichief.adapter.RecyclerFoodAdapter
import com.example.nutrichief.datamodels.Food
import com.example.nutrichief.datamodels.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class SearchFragment : Fragment(), RecyclerFoodAdapter.OnItemClickListener {

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var dishAdapter: RecyclerFoodAdapter
    private lateinit var ingredientAdapter: IngredientSearchAdapter
    private lateinit var allDishes: List<Food>
    private lateinit var allIngredients: List<Ingredient>
    private var isSearchingIngredients = false
    private var savedSearchQuery = ""

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val dishBtn = view.findViewById<Button>(R.id.dishButton)
        val ingredientBtn = view.findViewById<Button>(R.id.ingredientButton)
        val searchBox = view.findViewById<SearchView>(R.id.search_menu_items)

        //set button colors
        ingredientBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
        dishBtn.setBackgroundColor(Color.parseColor("#6173DF"))

        searchRecyclerView = view.findViewById(R.id.ingre_recycler_view)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapters
        dishAdapter = RecyclerFoodAdapter(mutableListOf(), this)
        ingredientAdapter = IngredientSearchAdapter(mutableListOf())

        // Set the initial adapter to dishAdapter
        getAllDishes { food ->
            if (food != null) {
                allDishes = food
                dishAdapter.filterList(allDishes as MutableList<Food>)
                dishAdapter = RecyclerFoodAdapter(allDishes as MutableList<Food>, this)
                searchRecyclerView.adapter = dishAdapter
            } else {
                Log.e("Ingredients Search", "Failed to retrieve recipe ingredients")
            }
        }

        dishBtn.setOnClickListener {
            isSearchingIngredients = false
            dishBtn.setBackgroundColor(Color.parseColor("#6173DF"))
            ingredientBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            dishAdapter.filter.filter(savedSearchQuery)

            searchRecyclerView.adapter = dishAdapter

        }

        ingredientBtn.setOnClickListener {
            isSearchingIngredients = true
            ingredientBtn.setBackgroundColor(Color.parseColor("#6173DF"))
            dishBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))

            getAllIngredients { ingredients ->
                if (ingredients != null) {
                    allIngredients = ingredients
                    ingredientAdapter.filterList(allIngredients as MutableList<Ingredient>)
                    ingredientAdapter =
                        IngredientSearchAdapter(allIngredients as MutableList<Ingredient>)
                    ingredientAdapter.filter.filter(savedSearchQuery)
                    searchRecyclerView.adapter = ingredientAdapter
                } else {
                    Log.e("Ingredients Search", "Failed to retrieve recipe ingredients")
                }
            }
        }

        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                savedSearchQuery = newText ?: ""
                if (isSearchingIngredients) {
                    ingredientAdapter.filter.filter(newText)
                    return true
                } else {
                    dishAdapter.filter.filter(newText)
                    return true
                }
                return false
            }
        })
        return view
    }

    private fun getAllDishes(callback: (List<Food>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/food")
                    .post(
                        RequestBody.create(
                            "application/json".toMediaTypeOrNull(),
                            requestBody.toString()
                        )
                    )
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

                    val food = mutableListOf<Food>()
                    for (i in 0 until data.length()) {
                        val jsonFood: JSONObject = data.getJSONObject(i)
                        val foodItem = Food(
                            jsonFood.getInt("food_id"),
                            jsonFood.getString("food_name"),
                            jsonFood.getString("food_desc"),
                            jsonFood.getInt("food_ctime"),
                            jsonFood.getInt("food_ptime")
                        )
                        food.add(foodItem)
                    }
                    callback(food)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                // Handle the error here
                Log.e("RecipeDetail", "Failed to retrieve food: ${e.message}")
            }
        }
    }

    private fun getAllIngredients(callback: (List<Ingredient>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/ingre")
                    .post(
                        RequestBody.create(
                            "application/json".toMediaTypeOrNull(),
                            requestBody.toString()
                        )
                    )
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                if (!response.isSuccessful) {
                    throw IOException("Failed to retrieve ingredients")
                }

                val responseBody = response.body?.string()
                val resultJson = JSONObject(responseBody ?: "")
                val status = resultJson.optInt("status", 0)

                if (status == 1) {
                    val data = resultJson.optJSONArray("data")

                    val ingredients = mutableListOf<Ingredient>()
                    for (i in 0 until data.length()) {
                        val jsonIngredient: JSONObject = data.getJSONObject(i)
                        val ingredient = Ingredient(
                            jsonIngredient.getInt("ingre_id"),
                            jsonIngredient.getString("ingre_name"),
                            jsonIngredient.getDouble("ingre_price").toFloat(),
                            jsonIngredient.getInt("ingre_calo"),
                            jsonIngredient.getDouble("ingre_fat").toFloat(),
                            jsonIngredient.getDouble("ingre_protein").toFloat(),
                            jsonIngredient.getDouble("ingre_carb").toFloat(),
                            jsonIngredient.getString("ingre_img")
                        )
                        ingredients.add(ingredient)
                    }
                    callback(ingredients)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                // Handle the error here
                Log.e("RecipeDetail", "Failed to retrieve ingredients: ${e.message}")
            }
        }
    }

    override fun onFoodClick(item: Food) {
        val searchIntent = Intent(activity, RecipeDetailActivity::class.java)
        searchIntent.putExtra("food_id", item.food_id)
        startActivity(searchIntent)
    }

}
