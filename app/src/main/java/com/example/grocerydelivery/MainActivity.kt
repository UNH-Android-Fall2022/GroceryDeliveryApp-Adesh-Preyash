package com.example.grocerydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatDelegate
import com.example.grocerydelivery.databinding.ActivityMainBinding
import com.example.grocerydelivery.ui.cart.CartFragment
import com.example.grocerydelivery.ui.home.HomeFragment
import com.example.grocerydelivery.ui.profile.ProfileFragment
import com.example.grocerydelivery.ui.search.SearchFragment
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "GroceryDeliveryAndroidDebug"
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView? = binding.bottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_cart, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView?.setupWithNavController(navController)
    }
}

