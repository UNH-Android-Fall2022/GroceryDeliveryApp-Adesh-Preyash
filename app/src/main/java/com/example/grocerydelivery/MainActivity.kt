package com.example.grocerydelivery

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.grocerydelivery.databinding.ActivityMainBinding
import com.example.grocerydelivery.ui.home.HomeFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.buttonLogin.setOnClickListener{
            Log.d(ContentValues.TAG,"Login Button Clicked")
            val action= HomeFragmentDirections.actionNavigationHomeToLoginActivity()
            findNavController().navigate(action) }
        binding.buttonSignUp.setOnClickListener{
            Log.d(ContentValues.TAG,"Sign up Button Clicked")
            val action1= HomeFragmentDirections.actionNavigationHomeToRegisterActivity()
            findNavController().navigate(action1)}*/
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        /*val navView: BottomNavigationView? = binding.bottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_cart, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView?.setupWithNavController(navController)*/
    }


}

