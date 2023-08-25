package com.example.nutrichief

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.model.Food
import com.example.nutrichief.model.Meal

class MealPlanActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    private lateinit var recyclerViewMeal: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_plan)
        val breakfastFoods = mutableListOf(
            Food(1, "Banh Mi", "good",0, 10),
            Food(2, "Milk", "good", 0,0)
        )

        val lunchFoods = mutableListOf(
            Food(3, "Rice", "good",30, 5),
            Food(4, "Beer", "good", 0,0),
            Food(5, "Stir Fry Chicken", "good", 15, 15)
        )

        val dinnerFoods = mutableListOf(
            Food(3, "Rice", "good",30, 5),
            Food(6, "Beef Steak", "good", 15, 15)
        )
        val mealList = mutableListOf(
            Meal(mealType = "breakfast", foodList = breakfastFoods),
            Meal(mealType = "lunch", foodList = lunchFoods),
            Meal(mealType = "dinner", foodList = dinnerFoods)
        )

        progressBar = findViewById(R.id.prog_bar_carb)
        progressBar.progress = 60

        recyclerViewMeal = findViewById(R.id.recycler_view_meal)

        recyclerViewMeal.adapter = RecyclerMealAdapter(mealList)
        recyclerViewMeal.layoutManager = LinearLayoutManager(this)
    }
}