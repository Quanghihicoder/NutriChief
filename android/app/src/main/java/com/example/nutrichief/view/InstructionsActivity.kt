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
import com.squareup.picasso.Picasso
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

class InstructionsActivity : AppCompatActivity() {
    private var currentPage = 1
    private var totalPages = 0

    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var stepNumber: TextView
    private lateinit var recipeTitle: TextView
    private lateinit var recipeQty: TextView
    private lateinit var recipeDesc: TextView
    private var videoPath: String? = null

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
        val mediaController = MediaController(this)

        var cookingSteps = mutableListOf<RecipeIngredient>()

        val food_id = intent.getIntExtra("food_id", 1)

        if (videoPath == null) {
            videoPath = "https://www.shutterstock.com/shutterstock/videos/1009023404/preview/stock-footage-rapidly-chopping-onion-close-up-slow-mothion-red-onions-close-up-female-hands-cut-onions-in.webm"
        }

        getRecipeData(food_id) { recipeIngredients ->
            recipeIngredients?.let {
                runOnUiThread {
                    cookingSteps = it as MutableList<RecipeIngredient>

                    recipeTitle.text = cookingSteps[currentPage - 1].recipe_title
                    recipeQty.text = cookingSteps[currentPage - 1].recipe_qty.toString() + "g"
                    recipeDesc.text = cookingSteps[currentPage - 1].recipe_desc

                    totalPages = cookingSteps.size

                    videoPath = cookingSteps[currentPage - 1].media_url
                    mediaController.setAnchorView(videoView)
                    videoView.setMediaController(mediaController)
                    videoView.setVideoURI(Uri.parse(videoPath))

                    updateButtonVisibility()
                }
            } ?: run {
                // Handle the case when recipeIngredients is null (error occurred)
                Toast.makeText(this, "Failed to retrieve recipe ingredients", Toast.LENGTH_SHORT)
                    .show()
                Log.e("RecipeDetail", "Failed to retrieve recipe ingredients")
            }
        }

        previousButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                stepNumber.text = "Step $currentPage"
                updateButtonVisibility()
                // Handle page change here
                recipeTitle.text = cookingSteps[currentPage - 1].recipe_title
                recipeQty.text = cookingSteps[currentPage - 1].recipe_qty.toString() + "g"
                recipeDesc.text = cookingSteps[currentPage - 1].recipe_desc
                videoPath = cookingSteps[currentPage - 1].media_url
                mediaController.setAnchorView(videoView)
                videoView.setMediaController(mediaController)
                videoView.setVideoURI(Uri.parse(videoPath))
            }
        }

        nextButton.setOnClickListener {
            if (currentPage < totalPages) {
                currentPage++
                stepNumber.text = "Step $currentPage"
                updateButtonVisibility()
                // Handle page change here
                recipeTitle.text = cookingSteps[currentPage - 1].recipe_title
                recipeQty.text = cookingSteps[currentPage - 1].recipe_qty.toString() + "g"
                recipeDesc.text = cookingSteps[currentPage - 1].recipe_desc
                videoPath = cookingSteps[currentPage - 1].media_url
                mediaController.setAnchorView(videoView)
                videoView.setMediaController(mediaController)
                videoView.setVideoURI(Uri.parse(videoPath))
            }
        }
    }

    private fun updateButtonVisibility() {
        previousButton.visibility = if (currentPage == 1) View.GONE else View.VISIBLE
        if (currentPage == totalPages) {
            nextButton.text = "Finish"
            nextButton.setOnClickListener { finish() }
        }
        else View.VISIBLE
    }

    private fun getRecipeData(foodId: Int, callback: (List<RecipeIngredient>?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()
                requestBody.put("food_id", foodId)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/food/detail")
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
                        val recipeCalories = ingredientJson.getDouble("recipe_calories").toFloat()
                        val recipeProtein = ingredientJson.getDouble("recipe_protein").toFloat()
                        val recipeFat = ingredientJson.getDouble("recipe_fat").toFloat()
                        val recipeCarb = ingredientJson.getDouble("recipe_carb").toFloat()
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
}

