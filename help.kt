package com.example.lamestimate

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button




// ============ HELP ACTIVITy ============
class help : AppCompatActivity() {

    fun goTo(url: String){
        // https://stackoverflow.com/questions/5026349/how-to-open-a-website-when-a-button-is-clicked-in-android-application
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Connect items from help activity
        var goback_button = findViewById<Button>(R.id.goback_button)
        var readme_button = findViewById<Button>(R.id.readme_button)

        // Set up listeners for buttons
        goback_button.setOnClickListener{
            val intent = Intent(this@help, MainActivity::class.java)
            startActivity(intent)
        }

        readme_button.setOnClickListener {
            goTo("https://github.com/Adri6336/Lam-Estimate/blob/main/README.md")
        }

    }
}
