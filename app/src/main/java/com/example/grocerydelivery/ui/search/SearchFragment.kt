package com.example.grocerydelivery.ui.search

import android.R
import android.content.ContentValues.TAG
import com.example.grocerydelivery.ui.search.SearchViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.databinding.FragmentSearchBinding
import com.example.grocerydelivery.ui.cart.CartAdapter
import com.example.grocerydelivery.ui.cart.CartFragment
import com.example.grocerydelivery.ui.home.CategoryAdapter
import com.example.grocerydelivery.ui.home.CategoryFruitsFragment.Companion.allItemsList
import com.example.grocerydelivery.ui.home.CategoryItemCard
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var mRecyclerView : RecyclerView

    private val TAG="GroceryAndroidDebug"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val SearchRecyclerList: ArrayList<CategoryItemCard> = ArrayList()

        val searchView=binding.searchView
        val listAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            programmingLanguagesList
        )



        // on below line we are adding on query
        // listener for our search view.
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                for (item in allItemsList)
                {
                    if (item.Name.contains(query.toString()))
                    {
                        SearchRecyclerList.add(item)
                        mRecyclerView.adapter= CategoryAdapter(SearchRecyclerList, this@SearchFragment)
                    }

                }

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                for (item in allItemsList)
                {
                    if (item.Name.contains(newText.toString()))
                    {
                        SearchRecyclerList.add(item)
                        mRecyclerView.adapter= CategoryAdapter(SearchRecyclerList, this@SearchFragment)
                    }

                }
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                //listAdapter.filter.filter(newText)

                return false
            }
        })

        mRecyclerView= binding.recylerViewSearch
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter= CategoryAdapter(SearchRecyclerList, this)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}