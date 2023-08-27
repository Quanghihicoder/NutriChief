package com.example.nutrichief

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import com.example.nutrichief.view.UserProfileActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = findViewById<TextView>(R.id.test)
        val test2 = findViewById<TextView>(R.id.test2)
        test.setOnClickListener {
            val fragmentContainer: FragmentContainerView = findViewById(R.id.fragment_container)
            fragmentContainer.visibility = View.VISIBLE
        }

        test2.setOnClickListener {
            val loginIntent = Intent(this, UserProfileActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }
}