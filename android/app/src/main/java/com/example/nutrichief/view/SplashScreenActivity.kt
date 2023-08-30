package com.example.nutrichief.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.nutrichief.MainActivity
import com.example.nutrichief.R

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var appLogoBack: ImageView
    private lateinit var appTextBack: ImageView

    override fun onStart() {
        super.onStart()
        window.statusBarColor = resources.getColor(R.color.purple_theme_color)
        window.navigationBarColor = resources.getColor(R.color.purple_theme_color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        appLogoBack = findViewById(R.id.splash_screen_app_logo_back_iv)
        appTextBack = findViewById(R.id.splash_screen_app_logo_iv)

        //animations
        appLogoBack.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_decrease_anim))
        appTextBack.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_increase_anim))

        // Retrieve user email from Intent
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString("email", null)
        Log.i("intent", userEmail.toString())

        if (userEmail != null) {
            //go to home screen if already logged in
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }, 1200)
        } else {
            //go to register screen
            Handler().postDelayed({
                val intent = Intent(this, RecipeDetailActivity::class.java)
                startActivity(intent)
                finish()

            }, 1200)
        }
    }
}