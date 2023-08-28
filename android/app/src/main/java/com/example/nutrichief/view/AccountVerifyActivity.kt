package com.example.nutrichief.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.nutrichief.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class AccountVerifyActivity : AppCompatActivity() {

    private lateinit var verifyBtn: Button
    private lateinit var emailText: TextInputEditText

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_verify)

        verifyBtn = findViewById(R.id.verify_btn)

        verifyBtn.setOnClickListener{
            emailText = findViewById(R.id.email)
            val email = emailText.text.toString()
            sendOTP(email) { response, responseBody ->
                if (response.isSuccessful) {
                    Log.e("API Request","success")
                    val otpIntent = Intent(this, OtpVerifyActivity::class.java)
                    startActivity(otpIntent)

                    // Save email data to SharedPreferences
                    val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.putString("email", email)
                    editor.apply()

                    finish()
                } else {
                    Log.e("API Request","fail")
                }
            }
        }
    }

    private fun sendOTP(userEmail: String, callback: (Response, String?) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val requestBody = JSONObject()
                requestBody.put("user_email", userEmail)

                val request = Request.Builder()
                    .url("http://10.0.2.2:8001/apis/otp/create")
                    .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody.toString()))
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        callback(response, responseBody)
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("API Request", "Failed to send OTP", e)
                    }
                })
            } catch (e: Exception) {
                // Registration failed, handle the error
            }
        }
    }
}