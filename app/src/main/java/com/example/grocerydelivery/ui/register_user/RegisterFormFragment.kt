package com.example.grocerydelivery.ui.register_user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grocerydelivery.databinding.FragmentRegisterUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class RegisterFormFragment : Fragment() {

    private var _binding: FragmentRegisterUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private var currentUser: String=""
    private var users:MutableList<RegisterDetails> = arrayListOf()
    private val TAG="GroceryAndroidDebug"

    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(RegisterFormViewModel::class.java)

        _binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //user_authentication()
        auth = Firebase.auth
        //user_match()

        return root
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun performSignup()
    {
        val password=binding.passwordInput.toString()
        val username=binding.usernameInput.toString()


    }
/*
    fun createAccount(email: String,password: String)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    */

    fun user_authentication()
    {
        //println("first task"+ Thread.currentThread().name)
        val password=binding.passwordInput
        val username=binding.usernameInput

            db.collection("user").get()
                .addOnSuccessListener { documents ->
                    users = mutableListOf()
                    for (document in documents) {
                        Log.d("GROCERY db data", "${document.id}=> ${document.data}")
                        val user_info = document.toObject(RegisterDetails::class.java)

                        Log.d("GROCERY text output", user_info.username)
                        Log.d(TAG, "Reaching there 1")
                        users.add(user_info)
                        Log.d(TAG, "Reaching there 2")
                        for (user_details in users) {
                            println("Users:")
                            println(user_details)
                            Log.d(TAG, "Reaching there 3")
                            /*
                            binding.button1.setOnClickListener {
                                if (password.text.toString() == user_details.password.toString() && username.text.toString() == user_details.username.toString())
                                {
                                    currentUser=username.text.toString()
                                    Log.d(TAG, "Login details match")
                                }

                            }

                             */
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents", exception)
                    Log.d("GROCERY", "Error getting documents", exception)
                }

            return
        }



    fun user_match()
    {
        println("second task"+ Thread.currentThread().name)
        //delay(5000)
        Log.d(TAG, "Reaching there 4")
        for (user_details in users) {
            println("Outside db call Users:")
            println(user_details.username)

        }
    }
}
