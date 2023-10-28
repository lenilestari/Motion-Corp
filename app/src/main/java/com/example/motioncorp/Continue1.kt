package com.example.motioncorp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Continue1 : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue1)

        val continue1 = findViewById<TextView>(R.id.T_Next1)
        continue1.setOnClickListener {
            val intentContinue1 = Intent (this, Continue2::class.java)
            startActivity(intentContinue1)
        }

        val continue1Image = findViewById<ImageView>(R.id.Image_1)
        continue1Image.setOnClickListener {
            val intentContinue1Image = Intent (this, Continue2::class.java)
            startActivity(intentContinue1Image)
        }

    }
}