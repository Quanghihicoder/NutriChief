package com.example.nutrichief

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.model.Food
import com.example.nutrichief.model.Meal
import com.example.nutrichief.view.InstructionsActivity
import com.example.nutrichief.view.UserProfileActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_MEAL_LIST = "meal_list"

/**
 * A simple [Fragment] subclass.
 * Use the [MealPlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealPlanFragment : Fragment(), RecyclerMealAdapter.onMealCheckedChange {
    // TODO: Rename and change types of parameters
    private var mealList = mutableListOf<Meal>()
    private lateinit var recyclerViewMeal: RecyclerView
    private var date = LocalDate.now()
    private var mealPrefCalo: Float = 0.0f
    private var numOfMealChecked = 0

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealList = if (Build.VERSION.SDK_INT >= 33) {
                it.getParcelableArrayList(ARG_MEAL_LIST, Meal::class.java) as MutableList<Meal>
            } else {
                it.getParcelableArrayList<Meal>(ARG_MEAL_LIST) as MutableList<Meal>
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meal_plan, container, false)

        val textViewGeneratePlan = view.findViewById<TextView>(R.id.text_view_generate_plan)
        val layoutMealPlanOptions = view.findViewById<LinearLayout>(R.id.layout_meal_plan_options)
        val layoutSuggestedMealPlan =
            view.findViewById<FrameLayout>(R.id.layout_suggested_meal_plan)
        val textViewDate = view.findViewById<TextView>(R.id.text_view_date)
        val imageViewMealPlanActions =
            view.findViewById<ImageView>(R.id.image_view_meal_plan_actions)
        textViewDate.text = DateTimeFormatter.ofPattern("dd MMM yyyy").format(date)

        val imageUserAva = view.findViewById<ImageView>(R.id.img_user_ava)
        imageUserAva.setOnClickListener {
            val intent = Intent(activity, UserProfileActivity::class.java)
            startActivity(intent)
        }

        val progressBarCalories = view.findViewById<ProgressBar>(R.id.prog_bar_calories)
        progressBarCalories.progress = 0

        val textDailyRecommendedCarb = view.findViewById<TextView>(R.id.text_daily_recommended_carb)
        val textDailyRecommendedFat = view.findViewById<TextView>(R.id.text_daily_recommended_fat)
        val textDailyRecommendedProtein =
            view.findViewById<TextView>(R.id.text_daily_recommended_protein)
        val textDailyRecommendedCalo = view.findViewById<TextView>(R.id.text_daily_recommended_calo)

        recyclerViewMeal = view.findViewById(R.id.recycler_view_meal)
        recyclerViewMeal.adapter = RecyclerMealAdapter(mealList, this)
        recyclerViewMeal.layoutManager = LinearLayoutManager(activity)


        val imageViewBackward = view.findViewById<ImageView>(R.id.image_view_backward)
        val imageViewForward = view.findViewById<ImageView>(R.id.image_view_forward)

        imageViewBackward.setOnClickListener {
            date = date.minusDays(1)
            textViewDate.text = DateTimeFormatter.ofPattern("dd MMM yyyy").format(date)
        }

        imageViewForward.setOnClickListener {
            date = date.plusDays(1)
            textViewDate.text = DateTimeFormatter.ofPattern("dd MMM yyyy").format(date)
        }

        imageViewMealPlanActions.setOnClickListener {
            val popUpMenu = PopupMenu(requireContext(), it)
            val menuInflater = popUpMenu.menuInflater
            menuInflater.inflate(R.menu.meal_plan_action_menu, popUpMenu.menu)
            popUpMenu.show()
        }

        textViewGeneratePlan.setOnClickListener {
            val sharedPrefs = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val userId = sharedPrefs?.getInt("user_id", 0)
            getGeneratedMealPlan(
                userId!!,
                DateTimeFormatter.ofPattern("yy-MM-dd").format(date)
            ) { generatedMeals ->
                var totalCalories = 0.0f
                for ( i in 0 until mealList.size) {
                    for (j in 0 until mealList[i].foodList.size) {
                        totalCalories += mealList[i].foodList[j].foodCalories
                    }
                }
                textDailyRecommendedCalo.text = totalCalories.toInt().toString()
                textDailyRecommendedCarb.text = "${(totalCalories * 0.4f / 4).toInt()}g"
                textDailyRecommendedProtein.text = "${(totalCalories * 0.3f / 4).toInt()}g"
                textDailyRecommendedFat.text = "${(totalCalories * 0.3f / 9).toInt()}g"

                layoutMealPlanOptions.visibility = View.GONE
                layoutSuggestedMealPlan.visibility = View.VISIBLE
                recyclerViewMeal.adapter = RecyclerMealAdapter(generatedMeals as MutableList, this)
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (mealList.isNotEmpty()) {
            val layoutMealPlanOptions =
                this.view?.findViewById<LinearLayout>(R.id.layout_meal_plan_options)
            val layoutSuggestedMealPlan =
                this.view?.findViewById<FrameLayout>(R.id.layout_suggested_meal_plan)
            layoutMealPlanOptions?.visibility = View.GONE
            layoutSuggestedMealPlan?.visibility = View.VISIBLE
        }
    }

    // implement onMealCheckInterface
    @SuppressLint("SetTextI18n")
    override fun onMealCheckChange(meal: Meal, mealCheck: Int) {

//        else {
//            println("Meal ${meal.mealType} is unmarked")
//        }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (mealCheck == 1) {
                    println("Meal ${meal.mealType} is marked as consumed")
                    println("Meal ${meal.mealType} food: ${meal.foodList}")

                    val requestBody = JSONObject()

                    requestBody.put("user_id", 2)
                    requestBody.put("meal_date", date)
                    requestBody.put("meal_checked", mealCheck)

                    val request = Request.Builder()
                        .url("http://10.0.2.2:8001/apis/meal/update")
                        .post(
                            requestBody.toString()
                                .toRequestBody("application/json".toMediaTypeOrNull())
                        )
                        .build()

                    val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                    if (!response.isSuccessful) {
                        throw IOException("Cannot get suggested meal")
                    }

                    val responseBody = response.body?.string()
                    val resultJson = JSONObject(responseBody ?: "")
                    val status = resultJson.optInt("status", 0)

                    if (status == 1) {
                        val randomIncrementStart = 100 / mealList.size - 5
                        val randomIncrementEnd = 100 / mealList.size + 5

                        view?.let {
                            val progressBarCarb = it.findViewById<ProgressBar>(R.id.prog_bar_carb)
                            val progressBarProtein = it.findViewById<ProgressBar>(R.id.prog_bar_protein)
                            val progressBarFat = it.findViewById<ProgressBar>(R.id.prog_bar_fat)
                            val progressBarCalories = it.findViewById<ProgressBar>(R.id.prog_bar_calories)

                            val textViewCarb = it.findViewById<TextView>(R.id.text_progress_carb)
                            val textViewProtein = it.findViewById<TextView>(R.id.text_progress_protein)
                            val textViewFat = it.findViewById<TextView>(R.id.text_progress_fat)
                            val textViewCalories = it.findViewById<TextView>(R.id.text_progress_calories)

                            println("Mealist size is: ${mealList.size}")
                            if (mealList.size - numOfMealChecked == 1) {
                                progressBarCarb.progress = 100
                                progressBarProtein.progress = 100
                                progressBarFat.progress = 100
                                progressBarCalories.progress = 100

                                textViewCarb.text = "100%"
                                textViewProtein.text = "100%"
                                textViewFat.text = "100%"
                                textViewCalories.text = "100%"
                            } else {
                                println("Random value: ${(Random.nextFloat() * (randomIncrementEnd - randomIncrementStart) + randomIncrementStart).toInt()}")
                                progressBarCarb.progress += (Random.nextFloat() * (randomIncrementEnd - randomIncrementStart) + randomIncrementStart).toInt()
                                progressBarProtein.progress += (Random.nextFloat() * (randomIncrementEnd - randomIncrementStart) + randomIncrementStart).toInt()
                                progressBarFat.progress += (Random.nextFloat() * (randomIncrementEnd - randomIncrementStart) + randomIncrementStart).toInt()
                                progressBarCalories.progress += (Random.nextFloat() * (randomIncrementEnd - randomIncrementStart) + randomIncrementStart).toInt()

                                textViewCarb.text = "${progressBarCarb.progress}%"
                                textViewProtein.text = "${progressBarProtein.progress}%"
                                textViewFat.text = "${progressBarFat.progress}%"
                                textViewCalories.text = "${progressBarCalories.progress}%"
                            }
                            numOfMealChecked++
                            println("Number of meal checked: ${numOfMealChecked}")
                        }
                    }
                }

            } catch (e: Exception) {
                // Handle the error here
                Log.e("RecipeDetail", "Failed to retrieve food: ${e.message}")
            }
        }
    }

    private fun getGeneratedMealPlan(
        userId: Int,
        mealDate: String,
        resultHandleFunction: (List<Meal>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()

                requestBody.put("user_id", userId)
                requestBody.put("meal_date", mealDate)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/meal/create")
                    .post(
                        requestBody.toString()
                            .toRequestBody("application/json".toMediaTypeOrNull())
                    )
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                if (!response.isSuccessful) {
                    throw IOException("Cannot get suggested meal")
                }

                val responseBody = response.body?.string()
                val resultJson = JSONObject(responseBody ?: "")
                val status = resultJson.optInt("status", 0)

                if (status == 1) {
                    val mealJson = resultJson.optJSONArray("data")
                    for (i in 0 until mealJson?.length() as Int) {
                        val foodJson = mealJson.getJSONObject(i)
                        val foodList = listOf(
                            Food(
                                foodJson.optInt("food_id"),
                                foodJson.optString("food_name", ""),
                                foodJson.optString("food_desc", ""),
                                foodJson.optInt("food_ctime"),
                                foodJson.optInt("food_ptime"),
                                foodJson.optDouble("food_price").toFloat(),
                                foodJson.optDouble("food_calories").toFloat(),
                                foodJson.optDouble("food_carb").toFloat(),
                                foodJson.optDouble("food_fat").toFloat(),
                                foodJson.optDouble("food_protein").toFloat()
                            )
                        )
                        mealList.add(Meal("Meal ${i + 1}", foodList))
                    }
                    resultHandleFunction(mealList)
                } else {
                    resultHandleFunction(null)
                }

            } catch (e: Exception) {
                // Handle the error here
                resultHandleFunction(null)
                Log.e("RecipeDetail", "Failed to retrieve food: ${e.message}")
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(mealList: ArrayList<Meal>) =
            MealPlanFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_MEAL_LIST, mealList)
                }
            }
    }


}