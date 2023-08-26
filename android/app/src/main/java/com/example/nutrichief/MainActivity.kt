package com.example.nutrichief

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.test)
        text.setOnClickListener {
            val fragmentContainer: FragmentContainerView = findViewById(R.id.fragment_container)
            fragmentContainer.visibility = View.VISIBLE

//            val intent = Intent(this, SearchActivity::class.java)
//            startActivity(intent)
        }
    }
}