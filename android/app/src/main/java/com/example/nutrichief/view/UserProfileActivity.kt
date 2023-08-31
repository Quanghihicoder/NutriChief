package com.example.nutrichief.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate

class UserProfileActivity : AppCompatActivity() {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private var actLevelString : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val fullName = findViewById<TextView>(R.id.profile_name)
        val email = findViewById<TextView>(R.id.profile_email)
        val gender = findViewById<TextView>(R.id.profile_gender)
        val age = findViewById<TextView>(R.id.profile_age)
        val height = findViewById<TextView>(R.id.profile_height)
        val weight = findViewById<TextView>(R.id.profile_weight)
        val actLevel = findViewById<TextView>(R.id.profile_activityLevel)
        val bmi = findViewById<TextView>(R.id.profile_bmi)
        val tdee = findViewById<TextView>(R.id.profile_tdee)
        val update = findViewById<ImageView>(R.id.update_profile)
        val currentOrderBtn = findViewById<ImageView>(R.id.current_order)

        update.setOnClickListener {
            startActivity( Intent(this, UserProfileSettingsActivity::class.java) )
            finish()
        }

        currentOrderBtn.setOnClickListener {
            startActivity( Intent(this, CurrentOrderActivity::class.java) )
            finish()
        }

//        val userEmail = "chie.bow.gu@gmail.com"
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString("user_email", "") ?: ""
        val userId = sharedPrefs.getInt("user_id", 0)

        fetchUserProfile(userId) { user ->
            user?.let {
                // Populate user profile data to TextViews
                fullName.text = it.user_name
                email.text = it.user_email

                var genderProfile = if (it.user_gender == 0) "Female" else "Male"
                gender.text = genderProfile
                val userBirth = it.user_year_of_birth
                val currentYear = LocalDate.now().year
                if (userBirth != null) {
                    age.text = (currentYear - userBirth.toInt()).toString()
                }
                height.text = "${it.user_height} cm"
                weight.text = "${it.user_weight} kg"

                var actLevelProfile = it.user_activity_level
                if (actLevelProfile != null) {
                    if (actLevelProfile.toInt() == 1) {
                        actLevelString = "Sedentary"
                    } else if (actLevelProfile.toInt() == 2) {
                        actLevelString = "Lightly active"
                    } else if (actLevelProfile.toInt() == 3) {
                        actLevelString = "Moderately active"
                    } else if (actLevelProfile.toInt() == 4) {
                        actLevelString = "Very active"
                    } else if (actLevelProfile.toInt() == 5) {
                        actLevelString = "Super active"
                    }
                }

                actLevel.text = actLevelString
                bmi.text = it.user_bmi.toString() + it.user_bmi?.let { it1 -> bmiRate(it1) }
                tdee.text = it.user_tdee.toString() + " calories per day"

            } ?: run {
                // Handle the case when user is null (error occurred)
                Toast.makeText(this, "Failed to retrieve user profile", Toast.LENGTH_SHORT).show()
                Log.e("UserProfile", "Failed to retrieve user profile")
            }
        }
    }

    fun goBack(view: View) { onBackPressed() }

    private fun fetchUserProfile(userId: Int, callback: (User?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()
                requestBody.put("user_id", userId)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/user/get")
                    .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody.toString()))
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                if (!response.isSuccessful) {
                    throw IOException("Failed to retrieve user profile")
                }

                val responseBody = response.body?.string()
                val resultJson = JSONObject(responseBody ?: "")
                val status = resultJson.optInt("status", 0)

                if (status == 1) {
                    val data = resultJson.optJSONArray("data")
                    val user = data?.optJSONObject(0)
                    val userObj = jacksonObjectMapper().readValue(user?.toString() ?: "", User::class.java)
                    callback(userObj)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                // Handle the error here
                callback(null)
                Log.e("UserProfile", "Failed to retrieve user profile: ${e.message}")
            }
        }
    }

    private fun bmiRate (bmi : Float) : String {
        if (bmi < 18.5 ) return  " Underweight"
        else if (bmi in 18.5..25.0) return " Healthy"
        else if (bmi in 25.0..30.0) return " Overweight"
        else return  " Obesity"
    }
}
