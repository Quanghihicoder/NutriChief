package com.example.nutrichief.view

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

class SearchFragment : Fragment() {

private lateinit var searchRecyclerView: RecyclerView
//    private lateinit var dishAdapter: DishAdapter
    private lateinit var ingredientAdapter: IngredientSearchAdapter
//    private lateinit var allDishes: List<Dish>
    private lateinit var allIngredients: List<Ingredient>
    private var isSearchingIngredients = false

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

        searchRecyclerView = view.findViewById(R.id.ingre_recycler_view)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapters
//        dishAdapter = DishAdapter(requireContext(), mutableListOf())
        ingredientAdapter = IngredientSearchAdapter(mutableListOf())

        // Set the initial adapter to dishAdapter
//        searchRecyclerView.adapter = dishAdapter

        dishBtn.setOnClickListener {
            isSearchingIngredients = false
//            searchRecyclerView.adapter = dishAdapter
            // Replace this with your actual data retrieval method for dishes
//            allDishes = getAllDishes()
//            dishAdapter.filterList(allDishes)
        }

        ingredientBtn.setOnClickListener {
            isSearchingIngredients = true

            getAllIngredients { ingredients ->
                if (ingredients != null) {
                    allIngredients = ingredients
                    ingredientAdapter.filterList(allIngredients as MutableList<Ingredient>)
                    ingredientAdapter = IngredientSearchAdapter(allIngredients as MutableList<Ingredient>)
                    searchRecyclerView.adapter = ingredientAdapter
                } else {
                    Log.e("Ingredients Search", "Failed to retrieve recipe ingredients")
                }
            }
//            ingredientAdapter.filter.filter(searchBox.query) // Apply search filter


        }

        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchFragment", "New text: $newText")
                if (isSearchingIngredients) {
                    ingredientAdapter.filter.filter(newText)
                    return true
                }
                return false
            }
        })
        return view
    }

    private fun getAllIngredients(callback: (List<Ingredient>?) -> Unit){
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
                    throw IOException("Failed to retrieve recipe ingredients")
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
            }catch (e: Exception) {
                // Handle the error here
                Log.e("RecipeDetail", "Failed to retrieve recipe ingredients: ${e.message}")
            }
        }
    }
}
