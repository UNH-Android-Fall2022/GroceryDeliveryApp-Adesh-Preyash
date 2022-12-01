package com.example.grocerydelivery.ui.home.Categories.Breads

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
import com.example.grocerydelivery.databinding.FragmentCatgeoryBreadsBinding
import com.example.grocerydelivery.ui.home.Categories.Fruits.CategoryFruitsFragment

import com.example.grocerydelivery.ui.home.CategoryAdapter
import com.example.grocerydelivery.ui.home.CategoryItemCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase


class CategoryBreadsFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryBreadsFragment()
        var allItemsList: ArrayList<CategoryItemCard> = ArrayList()

    }
    private var _binding: FragmentCatgeoryBreadsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var viewModel: CategoryBreadsViewModel
    private var mAuth: FirebaseAuth? = null
    private val db = Firebase.firestore
    private lateinit var current_item: CategoryItemCard
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notificationsViewModel =
            ViewModelProvider(this).get(CategoryBreadsViewModel::class.java)

        _binding = FragmentCatgeoryBreadsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val categoryRecyclerList: ArrayList<CategoryItemCard> = ArrayList()
        Log.d("GroceryDeliverFirebaseAndroidDebug","Fetching Breads from Firebase")
        firestoreDb = FirebaseFirestore.getInstance ()
        val Reference=firestoreDb.collection("/product_category/breads/breads_list")
        Reference.addSnapshotListener{snapshot,exception->
            if (exception != null || snapshot == null)
            {
                Log. e(TAG,  "Exception when querying posts", exception)
                return@addSnapshotListener
            }

            val itemList=snapshot.toObjects<CategoryBreadsData>()
            Log.d("CATEGORY_DB_CALL", "DB CALL SUCCESS")
            for (item in itemList)
            {
                current_item= CategoryItemCard(
                    item.imageSrc,item.Name,item.Size,item.Color,item.Price
                )
                if (!categoryRecyclerList.contains(current_item)) {
                    categoryRecyclerList.add(current_item)
                    CategoryFruitsFragment.allItemsList.add(current_item)                }
                mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

            }
        }
        mRecyclerView= binding.recylerViewCategoryBreads
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