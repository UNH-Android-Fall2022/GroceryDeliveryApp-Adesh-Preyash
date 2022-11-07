package com.example.grocerydelivery.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        /*
        binding.buttonLogin.setOnClickListener{
            Log.d(ContentValues.TAG,"Login Button Clicked")
            val action=HomeFragmentDirections.actionNavigationHomeToLoginActivity()
            findNavController().navigate(action) }
        binding.buttonSignUp.setOnClickListener{
            Log.d(ContentValues.TAG,"Sign up Button Clicked")
            val action1=HomeFragmentDirections.actionNavigationHomeToRegisterActivity()
            findNavController().navigate(action1)}*/


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}