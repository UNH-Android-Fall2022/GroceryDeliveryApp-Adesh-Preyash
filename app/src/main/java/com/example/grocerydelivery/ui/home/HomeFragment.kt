

package com.example.grocerydelivery.ui.home

import android.content.ContentValues
import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


open class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val TAG="GroceryAndroidDebug"
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val notificationsViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Setting clickable image buttons as Category choice
        binding.fruits.setOnClickListener{
            categoriesFruits()
        }
        binding.vegetables.setOnClickListener{
            categoriesVegetables()
        }
        binding.dairy.setOnClickListener {
            categoriesDairy()
        }
        binding.breads.setOnClickListener {
            categoriesBreads()
        }
        binding.cleaning.setOnClickListener {
            categoriesCleaning()
        }
        binding.cosmetics.setOnClickListener {
            categoriesCosmetics()
        }
        return root
    }


    public fun categoriesCosmetics() {
        val action=HomeFragmentDirections.actionNavigationHomeToCategoryCosmeticsFragment()
        findNavController().navigate(action)
    }

    private fun categoriesCleaning() {
        val action=HomeFragmentDirections.actionNavigationHomeToCategoryCleaningFragment()
        findNavController().navigate(action)
    }

    private fun categoriesBreads() {
        val action=HomeFragmentDirections.actionNavigationHomeToCategoryBreadsFragment()
        findNavController().navigate(action)
    }

    private fun categoriesDairy() {
        val action=HomeFragmentDirections.actionNavigationHomeToCategoryDairyFragment()
        findNavController().navigate(action)
    }

    private fun categoriesVegetables() {
        val action=HomeFragmentDirections.actionNavigationHomeToCategoryVegetablesFragment()
        findNavController().navigate(action)
    }

    private fun categoriesFruits() {

        val action=HomeFragmentDirections.actionNavigationHomeToCategoryFruitsFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}