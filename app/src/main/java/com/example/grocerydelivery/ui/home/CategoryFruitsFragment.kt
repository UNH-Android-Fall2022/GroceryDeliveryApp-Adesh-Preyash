package com.example.grocerydelivery.ui.home

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        /*for (fruit in fruits_list)
        {
            categoryRecyclerList.add(
                CategoryItemCard(
                    fruit.Name,
                    fruit.Size,
                    fruit.Color
                )
            )
        }*/

        mRecyclerView= binding.recylerViewCategoryFruits
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

        return root
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryFruitsViewModel::class.java)
        // TODO: Use the ViewModel


    }*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}