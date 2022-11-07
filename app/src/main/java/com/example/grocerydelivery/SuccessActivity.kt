package com.example.grocerydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.grocerydelivery.R
import com.example.grocerydelivery.R.*
import com.example.grocerydelivery.databinding.ActivitySuccessBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessBinding
    private val TAG = "GroceryDeliveryAndroidDebug"
    private var mAuth: FirebaseAuth? = null
    //private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        /*
        val firstFragment=HomeFragment()
        val secondFragment=SearchFragment()
        val thirdFragment=CartFragment()
        val fourthFragment=ProfileFragment()
         */
        //mAuth = FirebaseAuth.getInstance()
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView? = binding.bottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                id.navigation_home, id.navigation_search, id.navigation_cart, id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView?.setupWithNavController(navController)
    }
}