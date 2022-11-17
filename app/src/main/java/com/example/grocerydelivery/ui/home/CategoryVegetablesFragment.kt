package com.example.grocerydelivery.ui.home

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.databinding.FragmentCategoryVegetablesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase


class CategoryVegetablesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryVegetablesFragment()
    }
    private var _binding: FragmentCategoryVegetablesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mRecyclerView : RecyclerView
    private var mAuth: FirebaseAuth? = null
    private val db = Firebase.firestore
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var viewModel: CategoryVegetablesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notificationsViewModel =
            ViewModelProvider(this).get(CategoryVegetablesViewModel::class.java)

        _binding = FragmentCategoryVegetablesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val categoryRecyclerList: ArrayList<CategoryItemCard> = ArrayList()
        Log.d("GroceryDeliverFirebaseAndroidDebug","Fetching Vegetables from Firebase")
        firestoreDb = FirebaseFirestore.getInstance ()
        val fruitsReference=firestoreDb.collection("/product_category/vegetables/vegetables_list")
        fruitsReference.addSnapshotListener{snapshot,exception->
            if (exception != null || snapshot == null)
            {
                Log. e(ContentValues.TAG,  "Exception when querying posts", exception)
                return@addSnapshotListener
            }

            val List=snapshot.toObjects<CategoryFruitsData>()
            Log.d("FRUIT_CATEGORY_DB_CALL", "DB CALL SUCCESS")
            for (item in List)
            {

                categoryRecyclerList.add(
                    CategoryItemCard(
                        item.Name,item.Size,item.Color
                    )
                )
                mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

            }


        }
        mRecyclerView= binding.recylerViewCategoryVegetables
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