package com.example.nutrichief

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.model.Food
import com.example.nutrichief.model.Meal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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
    private var date = LocalDate.now()
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
        val layoutSuggestedMealPlan =
            view.findViewById<FrameLayout>(R.id.layout_suggested_meal_plan)
        val textViewDate = view.findViewById<TextView>(R.id.text_view_date)
        val imageViewMealPlanActions = view.findViewById<ImageView>(R.id.image_view_meal_plan_actions)
        textViewDate.text = DateTimeFormatter.ofPattern("dd MMM yyyy").format(date)

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

//        ArrayAdapter.createFromResource(
//            this.context!!, R.array.spinner_meal_plan_actions,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinnerMealPlanActions.adapter = adapter }
        imageViewMealPlanActions.setOnClickListener {
            val popUpMenu = PopupMenu(requireContext(), it)
            val menuInflater = popUpMenu.menuInflater
            menuInflater.inflate(R.menu.meal_plan_action_menu, popUpMenu.menu)

//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.getItemId()) {
//                    R.id.menu_item_option1 ->                 // Handle option 1 click
//                        return@setOnMenuItemClickListener true
//                    R.id.menu_item_option2 ->                 // Handle option 2 click
//                        return@setOnMenuItemClickListener true
//                    else -> return@setOnMenuItemClickListener false
//                }
//            }

            popUpMenu.show()
        }

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
            val layoutMealPlanOptions =
                this.view?.findViewById<LinearLayout>(R.id.layout_meal_plan_options)
            val layoutSuggestedMealPlan =
                this.view?.findViewById<FrameLayout>(R.id.layout_suggested_meal_plan)
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