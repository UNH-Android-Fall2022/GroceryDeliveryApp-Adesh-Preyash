package com.example.grocerydelivery.ui.home.Categories.Vegetables

import android.R
import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.activities.SplashActivity
import com.example.grocerydelivery.databinding.FragmentCategoryVegetablesBinding
import com.example.grocerydelivery.ui.home.Categories.CategoryData
import com.example.grocerydelivery.ui.home.Categories.Fruits.CategoryFruitsFragment
import com.example.grocerydelivery.ui.home.CategoryAdapter
import com.example.grocerydelivery.ui.home.CategoryItemCard
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
    private lateinit var current_item: CategoryItemCard
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

        mRecyclerView= binding.recylerViewCategoryVegetables
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        val categoryRecyclerList: ArrayList<CategoryItemCard> = ArrayList()
        for (item in SplashActivity.allProductsList)
        {
            current_item= CategoryItemCard(
                item.id,item.imageSrc,item.Name,item.Size,item.Color,item.Cost, item.Type,item.Uploaded //Added Boolean variable to indicate if product is uploaded or from Sample data
                )

                if ((!categoryRecyclerList.contains(current_item))&&(item.Type=="vegetables")) {
                    categoryRecyclerList.add(current_item)
                }
            mRecyclerView.adapter = CategoryAdapter(categoryRecyclerList, this)
        }

        return root
    }

    //Citation : https://stackoverflow.com/questions/40395067/android-back-button-not-working-in-fragment
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //required for back button to work
        setHasOptionsMenu(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(ContentValues.TAG, "Back Button Pressed")
        when (item.getItemId()) {
            R.id.home -> {
                Log.i(ContentValues.TAG, "home on backpressed")
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