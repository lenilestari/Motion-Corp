package com.example.motioncorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val isFirstInstall = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("FirstInstall", true)

        Handler().postDelayed({

            if (isFirstInstall) {

                PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putBoolean("FirstInstall", false)
                    .apply()
            }
            val targetActivity = if (isFirstInstall) {
                Continue1::class.java
            } else {
                MainActivity::class.java
            }
            startActivity(Intent(this, targetActivity))
            finish()
        }, SPLASH_TIME)
    }
}





