// Citation : Adapted from RegisterActivity.kt


package com.example.grocerydelivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.ActivityRegisterBinding
import com.example.grocerydelivery.utils.GSTextView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterSellerActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG="GroceryAndroidDebug"
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId : String?=""
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_seller)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        val root: View = binding.root


        auth= Firebase.auth
        FirebaseApp.initializeApp(this)

        val signupButton : Button = findViewById(R.id.buttonSignup1)

        signupButton.setOnClickListener{
            performSignup()
        }

        val register_customer= findViewById<GSTextView>(R.id.registerCustomer)
        register_customer.setOnClickListener{
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    //Function to implement Firebase Auth to signup user after 'signup' button is clicked
    private fun performSignup()
    {
        val inputPassword=findViewById<EditText>(R.id.password_input)
        val inputUsername=findViewById<EditText>(R.id.username_input)

        //if required fields are not provided, you cannot signup
        if (inputPassword.text.isEmpty() || inputUsername.text.isEmpty() )
        {
            Toast.makeText(baseContext, "Please fill all fields",
                Toast.LENGTH_SHORT).show()
        }

        val password=inputPassword.text.toString()
        val username=inputUsername.text.toString()

        //Citation: https://firebase.google.com/docs/auth/android/start#sign_up_new_users
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userid = auth.currentUser!!.uid

                    // Citation : https://stackoverflow.com/questions/72347820/storing-data-of-currently-signned-user-in-firebase-firestore-in-kotlin
                    val user = mapOf("email" to auth.currentUser!!.email,"type" to "seller ")
                    val userRef = db.collection("users")
                    userRef.document(userid).set(user)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                    val intent= Intent(this, ProductUploadActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Success",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Login failed.",
                    Toast.LENGTH_SHORT).show()
            }

    }
    public override fun onStart() {
        super.onStart()

        // Citation : https://firebase.google.com/docs/auth/android/start#check_current_auth_state
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload()
        }
    }
    }
