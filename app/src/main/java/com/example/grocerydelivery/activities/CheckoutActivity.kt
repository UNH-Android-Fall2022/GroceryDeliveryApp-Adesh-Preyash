package com.example.grocerydelivery.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.ActivityCheckoutBinding
import com.example.grocerydelivery.ui.cart.CartCard
import com.example.grocerydelivery.ui.cart.CartData
import com.example.grocerydelivery.ui.cart.CartFragment
import com.example.grocerydelivery.utils.GSEditText
import com.example.grocerydelivery.utils.StringUtil
import com.example.grocerydelivery.ui.cart.CartFragment.Companion
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.ktx.widget.PlaceSelectionError
import com.google.android.libraries.places.ktx.widget.PlaceSelectionSuccess
import com.google.android.libraries.places.ktx.widget.placeSelectionEvents
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi


class CheckoutActivity : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private var _binding: ActivityCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var responseView: TextView
    private val TAG="GroceryAndroidDebug"
    private lateinit var customer_name: GSEditText
    private lateinit var customer_phone: GSEditText
    private lateinit var addr_instructions: GSEditText
    private val db = Firebase.firestore

    //Suggested code - OptIn by Android Studio
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        _binding = ActivityCheckoutBinding.inflate(layoutInflater)
        val root: View = binding.root
        //Adding listener to "Submit" button
        val submitButton : Button = findViewById(R.id.orderSubmitBtn)
        submitButton.setOnClickListener {
           orderSubmitted()
        }


        //Citation: https://developers.google.com/learn/pathways/places-android
        // Initialize the SDK
        Places.initialize(applicationContext, "AIzaSyAhuTxWlEfoloHllPOTEdUSTc4k3AInQxg")
        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)

        // Setting up view objects
        responseView = findViewById(R.id.autocomplete_response_content)
        customer_name=findViewById(R.id.name)
        customer_phone=findViewById(R.id.Phone)
        addr_instructions=findViewById(R.id.address_instructions)


        //Citation: https://developers.google.com/codelabs/maps-platform/places-101-android-kotlin#7
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment
        autocompleteFragment.setCountries("US")
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )

        //Citation: https://developers.google.com/codelabs/maps-platform/places-101-android-kotlin#7
        // Listen to place selection events
        lifecycleScope.launchWhenCreated {
            autocompleteFragment.placeSelectionEvents().collect { event ->
                when (event) {
                    is PlaceSelectionSuccess -> {
                        val place = event.place
                        responseView.text = StringUtil.stringifyAutocompleteWidget(place, false)
                    }
                    is PlaceSelectionError -> Toast.makeText(
                        this@CheckoutActivity,
                        "Failed to get place '${event.status.statusMessage}'",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //After submit order is clicked, a function is called to write order details to firebase
    fun orderSubmitted()
    {
        val Name=customer_name.text.toString()
        val Ph=customer_phone.text.toString()
        val addr_ins=addr_instructions.text.toString()
        val address=responseView.text.toString()
        val cart_items=CartFragment.cartRecyclerList
        val cart_total=CartFragment.total.toString()
        writeOrderDetailsToFirebase(Name,Ph,addr_ins,address,cart_items,cart_total)
        Toast.makeText(
            this@CheckoutActivity,
            "Order Placed Successfully",
            Toast.LENGTH_SHORT
        ).show()
    }

    //This function has been adapted from IceBreaker live coding in classes
    private fun writeOrderDetailsToFirebase
                (name:String, phone:String,address_instructions:String,address:String,list:ArrayList<CartCard>,total:String)
    {
        //Creating hashmap for writing to Firebase
        val order= hashMapOf(
            "Customer_Name" to name,
            "Customer_Phone" to phone,
            "Address_Instructions" to address_instructions,
            "Address" to address,
            "order_items" to list,
            "Amount_to_be_paid" to total
        )
        //Writing order_details to Firebase
        db.collection("order_details")
            .add(order)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }
}