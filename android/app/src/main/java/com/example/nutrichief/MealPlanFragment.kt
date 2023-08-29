package com.example.nutrichief

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.nutrichief.model.Food
import com.example.nutrichief.model.Meal
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_MEAL_LIST = "meal_list"
/**
 * A simple [Fragment] subclass.
 * Use the [MealPlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealPlanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mealList = mutableListOf<Meal>()
    private lateinit var recyclerViewMeal: RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meal_plan, container, false)
        val textViewGeneratePlan = view.findViewById<TextView>(R.id.text_view_generate_plan)
        val layoutMealPlanOptions = view.findViewById<LinearLayout>(R.id.layout_meal_plan_options)
        val layoutSuggestedMealPlan = view.findViewById<FrameLayout>(R.id.layout_suggested_meal_plan)
        textViewGeneratePlan.setOnClickListener {
            val breakfastFoods = mutableListOf(
                Food(1, "Banh Mi", "good", 0, 10),
                Food(2, "Milk", "good", 0, 0)
            )

            val lunchFoods = mutableListOf(
                Food(3, "Rice", "good", 30, 5),
                Food(4, "Beer", "good", 0, 0),
                Food(5, "Stir Fry Chicken", "good", 15, 15)
            )

            val dinnerFoods = mutableListOf(
                Food(3, "Rice", "good", 30, 5),
                Food(6, "Beef Steak", "good", 15, 15)
            )
            val mealList = mutableListOf(
                Meal(mealType = "breakfast", foodList = breakfastFoods),
                Meal(mealType = "lunch", foodList = lunchFoods),
                Meal(mealType = "dinner", foodList = dinnerFoods)
            )
            this.mealList = mealList
            println(this.mealList)
            layoutMealPlanOptions.visibility = View.GONE
            layoutSuggestedMealPlan.visibility = View.VISIBLE
//            parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            recyclerViewMeal.adapter = RecyclerMealAdapter(this.mealList)
        }
        recyclerViewMeal = view.findViewById(R.id.recycler_view_meal)
        recyclerViewMeal.adapter = RecyclerMealAdapter(mealList)
        recyclerViewMeal.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (mealList.isNotEmpty()) {
            val layoutMealPlanOptions = this.view?.findViewById<LinearLayout>(R.id.layout_meal_plan_options)
            val layoutSuggestedMealPlan = this.view?.findViewById<FrameLayout>(R.id.layout_suggested_meal_plan)
            layoutMealPlanOptions?.visibility = View.GONE
            layoutSuggestedMealPlan?.visibility = View.VISIBLE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealPlanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(mealList: ArrayList<Meal>) =
            MealPlanFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_MEAL_LIST, mealList)
                }
            }
    }
}