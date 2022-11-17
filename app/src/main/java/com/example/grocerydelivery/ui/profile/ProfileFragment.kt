package com.example.grocerydelivery.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.FragmentProfileBinding
import com.example.grocerydelivery.ui.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val TAG="GroceryAndroidDebug"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            //val name = user.displayName
            val email = user.email
            //val photoUrl = user.photoUrl
            binding.emailView.text=email

            /*val phone=user.phoneNumber.toString()
            Log.d("GROCERY Phone number", phone)
            binding.phoneView.text=phone*/

            // Check if user's email is verified
            //val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            auth=Firebase.auth

            binding.buttonLogout.setOnClickListener{
                Firebase.auth.signOut()
                val action= ProfileFragmentDirections.actionNavigationProfileToMainActivity()
                findNavController().navigate(action)
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun performLogout()
    {

    }
}