package com.example.motioncorp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Continue2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue2)

        val continue2 = findViewById<TextView>(R.id.T_Next2)
        continue2.setOnClickListener {
            val intentContinue2 = Intent (this, Continue3::class.java)
            startActivity(intentContinue2)
        }

        val continue2Image = findViewById<ImageView>(R.id.Image_2)
        continue2Image.setOnClickListener {
            val intentContinue2Image = Intent (this, Continue3::class.java)
            startActivity(intentContinue2Image)
        }
    }
}