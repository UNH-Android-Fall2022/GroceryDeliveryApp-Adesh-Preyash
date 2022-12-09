package com.example.grocerydelivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowInsets
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.ActivitySplashBinding
import com.example.grocerydelivery.ui.home.Categories.CategoryData
import com.example.grocerydelivery.ui.home.CategoryItemCard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {

    companion object{
        var allProductsList: ArrayList<CategoryItemCard> = ArrayList()

    }
    private lateinit var binding: ActivitySplashBinding
    private val TAG="GroceryAndroidDebug"
    private val db = Firebase.firestore
    private lateinit var current_item: CategoryItemCard
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var mRecyclerView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)


        //Citation: Referred to YouTube tutorial - https://youtu.be/hoK9OOP1KZw
        @Suppress (  "DEPRECATION" )
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        @Suppress (  "DEPRECATION" )
        data_load("fruits")
        data_load("vegetables")
        data_load("breads")
        data_load("dairy")
        data_load("cleaning")
        data_load("cosmetics")
        Handler().postDelayed(
            {


                // Launch the Main Activity
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish() // Call this when your activity is done and should be closed.
            },
             2500
        )
    }

    fun data_load(category_name:String)
    {
        val categoryRecyclerList: ArrayList<CategoryItemCard> = ArrayList()
        Log.d(TAG,"Fetching $category_name from Firebase")
        firestoreDb = FirebaseFirestore.getInstance ()
        val Reference=firestoreDb.collection("/product_category/$category_name/${category_name}_list")
        Reference.addSnapshotListener{snapshot,exception->
            if (exception != null || snapshot == null)
            {
                Log. e(TAG,  "Exception when querying posts", exception)
                return@addSnapshotListener
            }

            val itemList=snapshot.toObjects<CategoryData>()
            Log.d(TAG, "DB CALL SUCCESS")
            for (item in itemList)
            {
                current_item= CategoryItemCard(
                    item.imageSrc,item.Name,item.Size,item.Color,item.Price
                )
                if (!categoryRecyclerList.contains(current_item)) {
                    categoryRecyclerList.add(current_item)
                    allProductsList.add(current_item)                }

            }
        }
    }
}