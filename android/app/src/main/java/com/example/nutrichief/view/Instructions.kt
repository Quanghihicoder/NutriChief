package com.example.nutrichief.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.Ingredient
import com.example.nutrichief.datamodels.RecipeIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class Instructions : AppCompatActivity() {
    private var currentPage = 1
    private var totalPages = 0

    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var stepNumber: TextView
    private lateinit var recipeTitle: TextView
    private lateinit var recipeQty: TextView
    private lateinit var recipeDesc: TextView

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking)

        stepNumber = findViewById(R.id.cooking_step)
        stepNumber.text = "Step $currentPage"

        recipeTitle = findViewById(R.id.recipeTitle)
        recipeQty = findViewById(R.id.recipeQty)
        recipeDesc = findViewById(R.id.recipeDesc)

        val buttonContainer: LinearLayout = findViewById(R.id.buttonContainer)

        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm"

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse(videoPath))

        val dummy = mutableListOf<RecipeIngredient>(
            RecipeIngredient(
                1,
                Ingredient(3, "Avocado", 100f, 101, 10f, 10f, 10f, ""),
                200f,
                "1 ripe avocado",
                "Cut the ripe avocado in half, remove the pit, and scoop out the flesh into a bowl. Use a fork to mash the avocado until it reaches your desired consistency."
            ),
            RecipeIngredient(
                1,
                Ingredient(113, "Bread", 101f, 102, 10f, 10f, 10f, ""),
                50f, "2 slices of bread", "Toast the bread."
            ),
            RecipeIngredient(
                1,
                Ingredient(93, "Bacon", 102f, 103, 10f, 10f, 10f, ""),
                100f, "Cook the bacon until crispy.", "2 slices of bacon"
            ),
        )

//        recipeTitle.text = dummy[currentPage - 1].recipeTitle
//        recipeQty.text = dummy[currentPage - 1].recipeQty.toString()
//        recipeDesc.text = dummy[currentPage - 1].recipeDesc

        val foodId = 1 // Replace with the desired food_id

        getRecipeData(foodId) { recipeIngredients ->
            recipeIngredients?.let {
                runOnUiThread {

                }
            } ?: run {
                // Handle the case when recipeIngredients is null (error occurred)
                Toast.makeText(this, "Failed to retrieve recipe ingredients", Toast.LENGTH_SHORT)
                    .show()
                Log.e("RecipeDetail", "Failed to retrieve recipe ingredients")
            }
        }

        recipeTitle.text = dummy[currentPage - 1].recipeTitle
        recipeQty.text = dummy[currentPage - 1].recipeQty.toString()
        recipeDesc.text = dummy[currentPage - 1].recipeDesc

        totalPages = dummy.size

        updateButtonVisibility()

        previousButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                stepNumber.text = "Step $currentPage"
                updateButtonVisibility()
                // Handle page change here
                recipeTitle.text = dummy[currentPage - 1].recipeTitle
                recipeQty.text = dummy[currentPage - 1].recipeQty.toString()
                recipeDesc.text = dummy[currentPage - 1].recipeDesc
            }
        }

        nextButton.setOnClickListener {
            if (currentPage < totalPages) {
                currentPage++
                stepNumber.text = "Step $currentPage"
                updateButtonVisibility()
                // Handle page change here
                recipeTitle.text = dummy[currentPage - 1].recipeTitle
                recipeQty.text = dummy[currentPage - 1].recipeQty.toString()
                recipeDesc.text = dummy[currentPage - 1].recipeDesc
            }
        }
    }

    private fun updateButtonVisibility() {
        previousButton.visibility = if (currentPage == 1) View.GONE else View.VISIBLE
        nextButton.visibility = if (currentPage == totalPages) View.GONE else View.VISIBLE
    }

    private fun getRecipeData(foodId: Int, callback: (List<RecipeIngredient>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()
                requestBody.put("food_id", foodId)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/recipe/ingre")
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

                    val recipeIngredients = mutableListOf<RecipeIngredient>()
                    for (i in 0 until data.length()) {
                        val ingredientJson = data.optJSONObject(i)
                        val ingredient = Ingredient(
                            ingredientJson.getInt("ingre_id"),
                            ingredientJson.getString("ingre_name"),

                        )
                        val recipeQty = ingredientJson.getDouble("recipe_qty").toFloat()

                        val recipeIngredient =
                            RecipeIngredient(foodId, ingredient, recipeQty, "", "")
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
}
