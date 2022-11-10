package com.example.grocerydelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.grocerydelivery.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityRegisterBinding
    private val db = Firebase.firestore
    private val TAG="GroceryAndroidDebug"
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId : String?=""
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var _binding: ActivityRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        val root: View = binding.root
        auth=Firebase.auth
        FirebaseApp.initializeApp(this)

        /*val sendOtp : Button = findViewById(R.id.sendOtp)

        sendOtp.setOnClickListener{
            val inputPassword=findViewById<EditText>(R.id.password_input)
            val inputUsername=findViewById<EditText>(R.id.username_input)
            val inputPhone=findViewById<EditText>(R.id.phoneNumber)
            val ph=inputPhone.text.toString()
            if (inputPassword.text.isEmpty() || inputUsername.text.isEmpty() || inputPhone.text.isEmpty())
            {
                Toast.makeText(baseContext, "Please fill all fields",
                    Toast.LENGTH_SHORT).show()

                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            Log.d(TAG, "PHONE NUMBER PASSED ($ph)")
            startPhoneNumberVerification(ph)
            Toast.makeText(baseContext, "OTP sent",
                Toast.LENGTH_SHORT).show()
        }*/

        val signupButton : Button = findViewById(R.id.buttonSignup1)

        signupButton.setOnClickListener{
            /*val inputOTP=findViewById<EditText>(R.id.OTP)
            val otp=inputOTP.text.toString()
            verifyPhoneNumberWithCode(storedVerificationId, otp)*/
            performSignup()
        }

        /*callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG,"Phone number verification completed : $credential")
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG,"Phone number verification failed", e)
            }
        }*/
    }

    private fun performSignup()
    {
        val inputPassword=findViewById<EditText>(R.id.password_input)
        val inputUsername=findViewById<EditText>(R.id.username_input)
        val inputPhone=findViewById<EditText>(R.id.phoneNumber)

        if (inputPassword.text.isEmpty() || inputUsername.text.isEmpty() )
        {
            Toast.makeText(baseContext, "Please fill all fields",
                Toast.LENGTH_SHORT).show()
        }

        val password=inputPassword.text.toString()
        val username=inputUsername.text.toString()
        val phone=inputPhone.text.toString()

        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val intent= Intent(this, SuccessActivity::class.java)
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
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload()
        }
    }
    private fun startPhoneNumberVerification(phoneNumber: String) {
// [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun verifyPhoneNumberWithCode (verificationId: String?, code: String) {
        Log.d(TAG, "Reach 1")
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        Log.d(TAG, "Reach 1.5")

        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential (credential: PhoneAuthCredential) {
        Log.d(TAG, "Reach 2")
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Reach 3")
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    Toast.makeText(this, "Welcome to the jungle :" + user, Toast.LENGTH_SHORT)
                        .show()
                    val intent= Intent(this, SuccessActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }
}