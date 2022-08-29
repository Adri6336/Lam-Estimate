package com.example.lamestimate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

// ============ HELP ACTIVITy ============
class help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Connect items from help activity
        var goback_button = findViewById<Button>(R.id.goback_button)

        // Set up listener for the return button
        goback_button.setOnClickListener{
            val intent = Intent(this@help, MainActivity::class.java)
            startActivity(intent)
        }











    }
}
