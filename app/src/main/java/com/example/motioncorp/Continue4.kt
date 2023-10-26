package com.example.motioncorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Continue4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue4)

        val continue4 = findViewById<TextView>(R.id.T_Next4)
        continue4.setOnClickListener {
            val intentContinue4 = Intent(this, MainActivity::class.java)
            startActivity(intentContinue4)
            finish()

        }
    }
}