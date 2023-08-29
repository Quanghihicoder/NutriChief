package com.example.nutrichief

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import com.example.nutrichief.view.UserProfileActivity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.nutrichief.model.Food
import com.example.nutrichief.model.Meal
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    @SuppressLint("MissingInflatedId")
    private lateinit var mealPlanFragment: MealPlanFragment
    private val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//         val test = findViewById<TextView>(R.id.test)
//         val test2 = findViewById<TextView>(R.id.test2)
//         test.setOnClickListener {
//             val fragmentContainer: FragmentContainerView = findViewById(R.id.fragment_container)
//             fragmentContainer.visibility = View.VISIBLE
//         }

//         test2.setOnClickListener {
//             val loginIntent = Intent(this, UserProfileActivity::class.java)
//             startActivity(loginIntent)
//             finish()
//         }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener(this)

        var mealList = mutableListOf<Meal>()
        mealPlanFragment = MealPlanFragment.newInstance(mealList as ArrayList<Meal>)
        replaceFragment(mealPlanFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_meal_plan -> {
                replaceFragment(mealPlanFragment)
            }
            R.id.nav_item_search -> {
                replaceFragment(this.searchFragment)
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_main, fragment)
        transaction.commit()
    }
}