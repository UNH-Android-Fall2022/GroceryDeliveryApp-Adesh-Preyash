package com.example.grocerydelivery.ui.home

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.R
import com.example.grocerydelivery.databinding.FragmentCategoryFruitsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryFruitsFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFruitsFragment()
    }
    private var _binding: FragmentCategoryFruitsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var viewModel: CategoryFruitsViewModel
    private var mAuth: FirebaseAuth? = null
    private val db = Firebase.firestore
    private lateinit var firestoreDb: FirebaseFirestore
    private var fruits_list:ArrayList<CategoryFruitsData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notificationsViewModel =
            ViewModelProvider(this).get(CategoryFruitsViewModel::class.java)

        _binding = FragmentCategoryFruitsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val categoryRecyclerList: ArrayList<CategoryItemCard> = ArrayList()



        Log.d("GroceryDeliverFirebaseAndroidDebug","Fetching Fruits from Firebase")


        firestoreDb = FirebaseFirestore.getInstance ()
        val fruitsReference=firestoreDb.collection("fruits")
        fruitsReference.addSnapshotListener{snapshot,exception->
            if (exception != null || snapshot == null)
            {
                Log. e(TAG,  "Exception when querying posts", exception)
            return@addSnapshotListener
        }

            val fruitList=snapshot.toObjects(CategoryFruitsData::class.java)
            for (fruit in fruitList)
            {
                Log.d("Snapshot call", fruit.toString())
                Log.d("Snapshot call", fruit.Name)
                Log.d("Snapshot call", fruit.Color)
                Log.d("Snapshot call", fruit.Size)
                categoryRecyclerList.add(
                    CategoryItemCard(
                        "Potato",
                        "Large",
                        "Brown"
                    )
                )

            }

            //parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            //childFragmentManager.beginTransaction().detach(this).attach(this).commit()

        }


        /*db.collection("fruits")
            .get()
            .addOnSuccessListener { documents ->
                fruits_list= mutableListOf()
                for(document in documents){
                    Log.d(TAG, "${document.id}=> ${document.data}")

                    val fruit=document.toObject(CategoryFruitsData::class.java)
                    //Log.d(TAG, fruit.id)
                    fruits_list.add(CategoryFruitsData(fruit.Name,fruit.Size,fruit.Size))
                    Log.d("Fruit db check",fruit.Name)
                    Log.d("Fruit db check",fruit.Size)
                    Log.d("Fruit db check",fruit.Color)

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents", exception)
            }*/




        for(categoryItem in CategoryFruitsList){
            Log.d("Category Fruits List : Fruit db check", categoryItem.toString())
            Log.d("Category Fruits List : Fruit db check",categoryItem.Name)
            Log.d("Category Fruits List : Fruit db check",categoryItem.Size)
            Log.d("Category Fruits List : Fruit db check",categoryItem.Color)
            categoryRecyclerList.add(
                CategoryItemCard(
                    categoryItem.Name,
                    categoryItem.Size,
                    categoryItem.Color
                )
            )
        }



        mRecyclerView= binding.recylerViewCategoryFruits
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    public fun refreshFragment (context: Context?) {
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment =
                    fragmentManager.findFragmentById(R.id.recyler_view_category_fruits)
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }

    }
}