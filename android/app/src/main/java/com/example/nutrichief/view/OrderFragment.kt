package com.example.nutrichief.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.FoodOrderAdapter
import com.example.nutrichief.datamodels.CartItem
import com.example.nutrichief.datamodels.MenuItem
import com.example.nutrichief.services.CartRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment(), FoodOrderAdapter.OnItemClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodOrderAdapter
    private lateinit var allDishes: List<MenuItem>
    private lateinit var cartRepository: CartRepository
    private var num: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        val searchBox = view.findViewById<SearchView>(R.id.search_menu_items)
        val cartBtn = view.findViewById<FloatingActionButton>(R.id.addPostBtn)

        // Initialize the cart repository
        cartRepository = CartRepository(requireContext())
        cartRepository.clearCartTable()
        orderRecyclerView = view.findViewById(R.id.order_recycler_view)
        orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        foodAdapter = FoodOrderAdapter(mutableListOf(), this)

        getAllDishes { food ->
            if (food != null) {
                allDishes = food
                foodAdapter.filterList(allDishes as MutableList<MenuItem>)
                foodAdapter = FoodOrderAdapter(allDishes as MutableList<MenuItem>, this)
                orderRecyclerView.adapter = foodAdapter
            } else {
                Log.e("Ingredients Search", "Failed to retrieve recipe ingredients")
            }
        }

        cartBtn.setOnClickListener {
            showBottomDialog()
        }

        return view
    }

    override fun onFoodClick(item: MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onPlusBtnClick(item: MenuItem) {
        item.food_quantity += 1
        val cartItem = CartItem(
            itemID = item.food_id,
            itemName = item.food_name,
            imageUrl = item.food_img,
            itemPrice = item.food_price,
            quantity = item.food_quantity,
            itemDesc = item.food_desc,
            foodID = item.food_id
        )

        cartRepository.increaseCartItemQuantity(
            cartItem.itemID,
            cartItem.itemName,
            cartItem.itemPrice,
            cartItem.itemDesc,
            cartItem.imageUrl,
            cartItem.quantity,
                item.food_id
        )

        Log.i("quantity: ", item.food_quantity.toString())
        Log.i("cart: ", cartItem.toString())
        num = item.food_quantity
    }

    //minus button handler
    override fun onMinusBtnClick(item: MenuItem) {
        GlobalScope.launch {
            if (item.food_quantity > 0) {
                item.food_quantity -= 1
                val cartItem = CartItem(
                    itemID = item.food_id,
                    itemName = item.food_name,
                    imageUrl = item.food_img,
                    itemPrice = item.food_price,
                    quantity = item.food_quantity,
                    itemDesc = item.food_desc,
                    foodID = item.food_id
                )

                if (item.food_quantity == 0) {
                    // If quantity becomes 0, remove the item from cart
                    cartRepository.removeFromCart(cartItem)

                } else {
                    // Update the cart item quantity
                    cartRepository.decreaseCartItemQuantity(cartItem.itemID, cartItem.itemName, cartItem.itemPrice, cartItem.itemDesc, cartItem.imageUrl, cartItem.quantity, item.food_id)
                }
                Log.i("quantity: ", item.food_quantity.toString())
                Log.i("cart: ", cartItem.toString())
                num = item.food_quantity
            }
        }
    }

    //show bottom fragment
    private fun showBottomDialog() {
        val bottomDialog = BottomSheetSelectedItemDialog()
        val bundle = Bundle()

        var totalPrice = 0.0f
        var totalItems = 0

        for (item in cartRepository.readCartData()) {
            totalPrice += item.itemPrice * item.quantity
            totalItems += item.quantity
        }

        bundle.putFloat("totalPrice", totalPrice)
        bundle.putInt("totalItems", totalItems)
        // bundle.putParcelableArrayList("orderedList", recyclerFoodAdapter.getOrderedList() as ArrayList<out Parcelable?>?)

        bottomDialog.arguments = bundle
        bottomDialog.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
    }

    private fun getAllDishes(callback: (List<MenuItem>?) -> Unit) {
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

                    val food = mutableListOf<MenuItem>()
                    for (i in 0 until data.length()) {
                        val jsonFood: JSONObject = data.getJSONObject(i)
                        val foodItem = MenuItem(
                            jsonFood.getInt("food_id"),
                            jsonFood.getString("food_name"),
                            jsonFood.getString("food_desc"),
                            jsonFood.getInt("food_ctime"),
                            jsonFood.getInt("food_ptime"),
                            jsonFood.getString("food_img"),
                            jsonFood.getDouble("food_price").toFloat(),
//                            num
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

//    override fun onFoodClick(item: Food) {
//        TODO("Not yet implemented")
//    }
}