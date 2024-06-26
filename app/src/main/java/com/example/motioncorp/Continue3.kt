package com.example.motioncorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Continue3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue3)

        val continue3 = findViewById<TextView>(R.id.T_Next3)
        continue3.setOnClickListener {
            val intentContinue3 = Intent(this, Continue4::class.java)
            startActivity(intentContinue3)
        }

        val continue3Image = findViewById<ImageView>(R.id.Image_3)
        continue3Image.setOnClickListener {
            val intentContinue3Image = Intent (this, Continue4::class.java)
            startActivity(intentContinue3Image)
        }

    }
}