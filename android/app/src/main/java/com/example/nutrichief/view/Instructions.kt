package com.example.nutrichief.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.example.nutrichief.R

class Instructions : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking)

        val buttonContainer: LinearLayout = findViewById(R.id.buttonContainer)

        // Replace this with your actual logic to retrieve the number from the database
        val stepNumber = getStepNumber()

        for (i in 0 until stepNumber) {
            val button = Button(this)
            button.text = "${i + 1}"
            button.setOnClickListener {
                // Handle button click here
            }
            buttonContainer.addView(button)
        }
    }

    private fun getStepNumber(): Int {
        // Replace this with your actual database retrieval logic
        // Return the number of buttons from the database

        return 4;
    }

}

