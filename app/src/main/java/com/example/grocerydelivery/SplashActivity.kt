package com.example.grocerydelivery

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress (  "DEPRECATION" )
        window.insetsController?.hide(WindowInsets.Type.statusBars())


        @Suppress (  "DEPRECATION" )
        Handler().postDelayed(
            {
                // Launch the Main Activity
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish() // Call this when your activity is done and should be closed.
            },
             2500
        )
    }
}