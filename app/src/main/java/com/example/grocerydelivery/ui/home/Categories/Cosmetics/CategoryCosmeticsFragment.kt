package com.example.grocerydelivery.ui.home.Categories.Cosmetics

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
import com.example.grocerydelivery.databinding.FragmentCategoryCosmeticsBinding
import com.example.grocerydelivery.ui.home.Categories.Fruits.CategoryFruitsFragment

import com.example.grocerydelivery.ui.home.CategoryAdapter
import com.example.grocerydelivery.ui.home.CategoryItemCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase


class CategoryCosmeticsFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryCosmeticsFragment()
        var allItemsList: ArrayList<CategoryItemCard> = ArrayList()

    }
    private var _binding: FragmentCategoryCosmeticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var viewModel: CategoryCosmeticsViewModel
    private var mAuth: FirebaseAuth? = null
    private val db = Firebase.firestore
    private lateinit var current_item: CategoryItemCard
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notificationsViewModel =
            ViewModelProvider(this).get(CategoryCosmeticsViewModel::class.java)

        _binding = FragmentCategoryCosmeticsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val categoryRecyclerList: ArrayList<CategoryItemCard> = ArrayList()
        Log.d("GroceryDeliverFirebaseAndroidDebug","Fetching from Firebase")
        firestoreDb = FirebaseFirestore.getInstance ()
        val Reference=firestoreDb.collection("/product_category/cosmetics/cosmetics_list")
        Reference.addSnapshotListener{snapshot,exception->
            if (exception != null || snapshot == null)
            {
                Log. e(TAG,  "Exception when querying posts", exception)
                return@addSnapshotListener
            }

            val itemList=snapshot.toObjects<CategoryCosmeticsData>()
            Log.d("CATEGORY_DB_CALL", "DB CALL SUCCESS")
            for (item in itemList)
            {
                current_item= CategoryItemCard(
                    item.imageSrc,item.Name,item.Size,item.Color,item.Price
                )
                if (!categoryRecyclerList.contains(current_item)) {
                    categoryRecyclerList.add(current_item)
                    CategoryFruitsFragment.allItemsList.add(current_item)
                }
                mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

            }
        }
        mRecyclerView= binding.recylerViewCategoryCosmetics
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