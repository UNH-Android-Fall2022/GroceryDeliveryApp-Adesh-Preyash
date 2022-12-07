package com.example.grocerydelivery.ui.home.Categories.Fruits

import android.R
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.databinding.FragmentCategoryFruitsBinding
import com.example.grocerydelivery.ui.home.CategoryAdapter
import com.example.grocerydelivery.ui.home.CategoryItemCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase


class CategoryFruitsFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFruitsFragment()
        var allItemsList: ArrayList<CategoryItemCard> = ArrayList()

    }
    private var _binding: FragmentCategoryFruitsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var viewModel: CategoryFruitsViewModel
    private var mAuth: FirebaseAuth? = null
    private val db = Firebase.firestore
    private lateinit var current_item: CategoryItemCard
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

            val itemList=snapshot.toObjects<CategoryFruitsData>()
            Log.d("FRUIT_CATEGORY_DB_CALL", "DB CALL SUCCESS")
            for (item in itemList)
            {
                current_item= CategoryItemCard(
                    item.imageSrc,item.Name,item.Size,item.Color,item.Price
                )
                if (!categoryRecyclerList.contains(current_item)) {
                    categoryRecyclerList.add(current_item)
                    allItemsList.add(current_item)
                }
                mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

            }
        }
        mRecyclerView= binding.recylerViewCategoryFruits
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)

        return root
    }

    //Citation : https://stackoverflow.com/questions/40395067/android-back-button-not-working-in-fragment
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //required for back button to work
        setHasOptionsMenu(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "Back Button Pressed")
        when (item.getItemId()) {
            R.id.home -> {
                Log.i(TAG, "home on backpressed")
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}