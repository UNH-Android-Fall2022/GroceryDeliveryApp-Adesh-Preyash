package com.example.grocerydelivery.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.databinding.FragmentCategoryFruitsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
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
    //private var fruits_list:ArrayList<CategoryFruitsData> = arrayListOf()

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
        val fruitsReference=firestoreDb.collection("/product_category/fruits/fruits_list")
        fruitsReference.addSnapshotListener{snapshot,exception->
            if (exception != null || snapshot == null)
            {
                Log. e(TAG,  "Exception when querying posts", exception)
            return@addSnapshotListener
        }

            val fruitList=snapshot.toObjects<CategoryFruitsData>()
            Log.d("FRUIT_CATEGORY_DB_CALL", "DB CALL SUCCESS")
            for (fruit in fruitList)
            {

                categoryRecyclerList.add(
                    CategoryItemCard(
                        fruit.Name,fruit.Size,fruit.Color
                    )
                )
                mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

            }


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

}