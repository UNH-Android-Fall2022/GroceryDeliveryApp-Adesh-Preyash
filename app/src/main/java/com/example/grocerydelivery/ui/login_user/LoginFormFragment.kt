package com.example.grocerydelivery.ui.login_user

import com.example.grocerydelivery.databinding.FragmentLoginUserBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class LoginFormFragment : Fragment() {

    private var _binding: FragmentLoginUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private var currentUser: String=""
    private var users:MutableList<LoginDetails> = arrayListOf()
    private val TAG="GroceryAndroidDebug"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(LoginFormViewModel::class.java)

        _binding = FragmentLoginUserBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //user_match()

        GlobalScope.launch {
            user_authentication()
            user_match()
        }


        //user_authentication()

        //user_match()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun user_authentication()
    {
        println("first task"+ Thread.currentThread().name)
        val password=binding.passwordInput
        val username=binding.usernameInput

        runBlocking(Dispatchers.IO)
        {
            db.collection("user").get()
                .addOnSuccessListener { documents ->
                    users = mutableListOf()
                    for (document in documents) {
                        Log.d("GROCERY db data", "${document.id}=> ${document.data}")
                        val user_info = document.toObject(LoginDetails::class.java)

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
                }.await()
        }
            return
        }



     suspend fun user_match()
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
