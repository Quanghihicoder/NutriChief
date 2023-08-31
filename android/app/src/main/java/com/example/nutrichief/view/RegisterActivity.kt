package com.example.nutrichief.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.nutrichief.MainActivity
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private var actLevelInt: Int? = null
    private lateinit var genderList: Spinner
    private var selectedGender: String? = null
    private val genders = arrayOf("Male", "Female")

    private lateinit var activeLevelList: Spinner
    private var selectedActiveLevel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val agreeCheckBox = findViewById<CheckBox>(R.id.register_agree_check_box)
        val registerBtn = findViewById<Button>(R.id.register_emp_btn)

        genderList = findViewById(R.id.gender)
        genderList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGender = parent?.getItemAtPosition(position) as? String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedGender = null
            }
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        genderList.adapter = adapter

        activeLevelList = findViewById(R.id.activeLevel)
        activeLevelList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedActiveLevel = parent?.getItemAtPosition(position) as? String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedActiveLevel = null
            }
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.activeLevel_array, // Replace with your own array resource name
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            activeLevelList.adapter = adapter
        }

        agreeCheckBox.setOnClickListener {
            registerBtn.isEnabled = agreeCheckBox.isChecked
        }

        registerBtn.setOnClickListener {
            val fullName = findViewById<TextInputEditText>(R.id.fullname).text.toString()
//            val email = "chie.bow.gu@gmail.com"
            val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val email = sharedPrefs.getString("user_email", "") ?: ""
            val userId = sharedPrefs.getInt("user_id", 0)

            val yearOfBirthText = findViewById<TextInputEditText>(R.id.yearofbirth).text.toString()
            val gender = selectedGender.toString()

            val weightText = findViewById<TextInputEditText>(R.id.weight).text.toString()
            val heightText = findViewById<TextInputEditText>(R.id.height).text.toString()

            if (selectedActiveLevel.toString() == "Sedentary") {
                actLevelInt = 1
            } else if (selectedActiveLevel.toString() == "Lightly active") {
                actLevelInt = 2
            } else if (selectedActiveLevel.toString() == "Moderately active") {
                actLevelInt = 3
            } else if (selectedActiveLevel.toString() == "Very active") {
                actLevelInt = 4
            } else if (selectedActiveLevel.toString() == "Super active") {
                actLevelInt = 5
            }

            if (fullName.isNotEmpty() && yearOfBirthText.isNotEmpty() &&
                weightText.isNotEmpty() &&
                heightText.isNotEmpty()
            ) {

                val yearOfBirth = yearOfBirthText.toInt()

                val genderInt = if (gender == "Female") 0 else 1

                val height = try {
                    heightText.toFloatOrNull() ?: 0.0f
                } catch (e: NumberFormatException) {
                    return@setOnClickListener
                }

                val weight = try {
                    weightText.toFloatOrNull() ?: 0.0f
                } catch (e: NumberFormatException) {
                    return@setOnClickListener
                }

                val user = User(
                    userId,
                    fullName,
                    email,
                    yearOfBirth,
                    genderInt,
                    height,
                    weight,
                    actLevelInt,
                    null,
                    null
                )

                GlobalScope.launch(Dispatchers.IO) {
                    registerUser(user) { response, errorMessage ->
                        Log.e("confirm", "clicked")
                        runOnUiThread {
                            if (response.isSuccessful) {
                                // Registration successful
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Registration successful",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val loginIntent =
                                    Intent(this@RegisterActivity, MainActivity::class.java)
                                val sharedPrefs =
                                    getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                val editor = sharedPrefs.edit()
                                editor.putString("user_name", fullName)
                                startActivity(loginIntent)
                                finish()
                            } else {
                                // Registration failed
                                if (errorMessage != null) {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Failed to register user",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun registerUser(customer: User, callback: (Response, String?) -> Unit) {
        GlobalScope.launch {
            try {
                val jsonMediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = jacksonObjectMapper().writeValueAsString(customer)
                    .toRequestBody(jsonMediaType)

                val requestBodyCreateMealPref = JSONObject()
                requestBodyCreateMealPref.put("user_id", customer.user_id)
                requestBodyCreateMealPref.put("pref_calo", customer.user_tdee)
                requestBodyCreateMealPref.put("pref_time", 60)
                requestBodyCreateMealPref.put("pref_goal", 1)
                requestBodyCreateMealPref.put("pref_date_range", 1)

                val requestCreateMealPref =
                    Request.Builder().url("http://localhost:8001/apis/mealpref/create")
                        .post(
                            requestBodyCreateMealPref.toString()
                                .toRequestBody("application/json".toMediaTypeOrNull())
                        )
                        .build()

                withContext(Dispatchers.IO) {
                    client.newCall(requestCreateMealPref).execute()
                }

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/user/update")
                    .post(requestBody)
                    .build()


                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        callback(response, responseBody)
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        // Handle network failure
                        callback(Response.Builder().code(-1).build(), e.message)
                    }
                })
            } catch (e: Exception) {
                // Handle other exceptions
                callback(Response.Builder().code(-1).build(), e.message)
            }
        }

    }
//    fun goBack(view: View) { onBackPressed() }
}
