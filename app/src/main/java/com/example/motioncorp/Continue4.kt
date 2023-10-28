package com.example.motioncorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class Continue4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue4)

        val continue4 = findViewById<TextView>(R.id.T_Next4)
        continue4.setOnClickListener {
            val intentContinue4 = Intent(this, MainActivity::class.java)
            startActivity(intentContinue4)
            Log.d("Continue4", "IntentContinue4 started")
//            finish()

        }

        val continue4Image = findViewById<ImageView>(R.id.Image_4)
        continue4Image.setOnClickListener{
            val intentContinue4Image = Intent (this, MainActivity::class.java)
            startActivity(intentContinue4Image)
        }
    }
}