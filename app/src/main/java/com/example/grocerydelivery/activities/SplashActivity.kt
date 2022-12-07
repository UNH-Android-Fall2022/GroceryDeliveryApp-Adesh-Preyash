package com.example.grocerydelivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)


        //Citation: Referred to YouTube tutorial - https://youtu.be/hoK9OOP1KZw
        @Suppress (  "DEPRECATION" )
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        @Suppress (  "DEPRECATION" )
        Handler().postDelayed(
            {
                // Launch the Main Activity
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish() // Call this when your activity is done and should be closed.
            },
             2500
        )
    }
}