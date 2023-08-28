package com.example.nutrichief

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.nutrichief.model.Food
import com.example.nutrichief.model.Meal
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mealPlanFragment: MealPlanFragment
    private val fm = this.supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fragmentMain: Fragment = findViewById(R.id.fragment_main)
        var mealList = mutableListOf<Meal>()

        mealPlanFragment = MealPlanFragment.newInstance(mealList as ArrayList<Meal>)
        fm.beginTransaction().replace(R.id.fragment_main, mealPlanFragment).commit()
    }

    fun getSuggestMealPlan() {

    }
}