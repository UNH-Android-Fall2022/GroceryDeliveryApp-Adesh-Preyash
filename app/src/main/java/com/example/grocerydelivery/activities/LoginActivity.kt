package com.example.grocerydelivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.grocerydelivery.R
import com.example.grocerydelivery.ui.home.Categories.CategoryData
import com.example.grocerydelivery.ui.home.CategoryItemCard
import com.example.grocerydelivery.utils.GSTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG="GroceryAndroidDebug"
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Citation : https://firebase.google.com/docs/auth/android/start#check_current_auth_state
        auth=Firebase.auth

        val loginButton : Button = findViewById(R.id.btn_login)

        loginButton.setOnClickListener{
            performLogin()
        }

        val register: GSTextView= findViewById(R.id.tv_dont_have_an_account)
        register.setOnClickListener{
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //Login as Seller button
        val login_seller: GSTextView= findViewById(R.id.loginSeller)
        login_seller.setOnClickListener{
            val intent= Intent(this, LoginSellerActivity::class.java)
            startActivity(intent)
        }



    }
    //Function to implement Firebase Auth to login user after 'Login' button is clicked
    fun performLogin()
    {
        Log.d("GROCERY text output", "Inside function call")
        val inputPassword=findViewById<EditText>(R.id.password_input)
        val inputUsername=findViewById<EditText>(R.id.username_input)

        //if required fields are not provided, you cannot login
        if (inputPassword.text.isEmpty() || inputUsername.text.isEmpty() )
        {
            Toast.makeText(baseContext, "Please fill all fields",
                Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val password=inputPassword.text.toString()
        val username=inputUsername.text.toString()


        //Citation : https://firebase.google.com/docs/auth/android/start#sign_in_existing_users
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val userid = auth.currentUser!!.uid
                    var current_user : UserData // data model class for User info

                    // Citation : https://firebase.google.com/docs/firestore/query-data/get-data#get_a_document
                    val docRef = db.collection("users").document(userid)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            current_user = document.toObject<UserData>()!!
                            if (current_user.type == "customer") {
                                Log.d(TAG,"Login Success as Customer")
                                Toast.makeText(
                                    baseContext, "Login Success as Customer",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, SuccessActivity::class.java)
                                startActivity(intent)
                            }
                            else
                            {
                                Log.d(TAG,"Invalid customer details")
                                Toast.makeText(
                                    baseContext, "Invalid customer details",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }

                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }

                }
                    else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}