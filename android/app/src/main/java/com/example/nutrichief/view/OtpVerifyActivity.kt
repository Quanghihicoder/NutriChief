package com.example.nutrichief.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.nutrichief.MainActivity
import com.example.nutrichief.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class OtpVerifyActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)

        var digit1 = findViewById<EditText>(R.id.digit1)
        var digit2 = findViewById<EditText>(R.id.digit2)
        var digit3 = findViewById<EditText>(R.id.digit3)
        var digit4 = findViewById<EditText>(R.id.digit4)
        var digit5 = findViewById<EditText>(R.id.digit5)
        var digit6 = findViewById<EditText>(R.id.digit6)

        val verifyButton = findViewById<Button>(R.id.verifyButton)

        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString("email", "") ?: ""

        verifyButton.setOnClickListener {
            val otpStringBuilder = StringBuilder()
            otpStringBuilder.append(digit1.text)
            otpStringBuilder.append(digit2.text)
            otpStringBuilder.append(digit3.text)
            otpStringBuilder.append(digit4.text)
            otpStringBuilder.append(digit5.text)
            otpStringBuilder.append(digit6.text)

            val otp = otpStringBuilder.toString()
            Log.e("OTP", otp)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val result = checkOTP(userEmail, otp)
                    val status = result.optInt("status", 0)
                    val message = result.optString("message", "")

                    if (status == 1) {
                        if (message == "Successfully verified OTP code") {
                            val data = result.optJSONArray("data")
                            val user = data?.optJSONObject(0)
                            val userName = user?.optString("user_name", null)
                            val userId = user?.optInt("user_id", 0)
                            Log.e("OTPuser", userId.toString())

                            if (userName === "null") {
                                val otpIntent = Intent(this@OtpVerifyActivity, RegisterActivity::class.java)
//                                otpIntent.putExtra("user_email", userEmail)
//                                otpIntent.putExtra("user_id", userId)
                                val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                val editor = sharedPrefs.edit()
                                editor.putString("user_email", userEmail)
                                if (userId != null) {
                                    editor.putInt("user_id", userId)
                                }
                                editor.apply()
                                startActivity(otpIntent)
                            } else {
                                val otpIntent = Intent(this@OtpVerifyActivity, MainActivity::class.java)
                                startActivity(otpIntent)
                            }
                        }
                    } else {
                        Log.e("OTPLog", "Fail")
                    }
                } catch (e: Exception) {
                    // Handle exceptions here
                    Log.e("OTPLog", "Fail", e)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkOTP(userEmail: String, otp: String): JSONObject {
        val requestBody = JSONObject()
        requestBody.put("user_email", userEmail)
        requestBody.put("otp_key", otp)

        val request = Request.Builder()
            .url("http://10.0.2.2:8001/apis/otp/verify")
            .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody.toString()))
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return JSONObject(responseBody)
    }
}
