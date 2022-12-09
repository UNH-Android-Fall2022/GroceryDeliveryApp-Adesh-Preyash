package com.example.grocerydelivery.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.activities.SplashActivity
import com.example.grocerydelivery.databinding.FragmentSearchBinding
import com.example.grocerydelivery.ui.home.CategoryAdapter
import com.example.grocerydelivery.ui.home.Categories.Fruits.CategoryFruitsFragment.Companion.allItemsList
import com.example.grocerydelivery.ui.home.CategoryItemCard
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var SearchRecyclerList: ArrayList<CategoryItemCard>
    private lateinit var tempArrayList: ArrayList<CategoryItemCard>

    private lateinit var mRecyclerView : RecyclerView

    private val TAG="GroceryAndroidDebug"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        Log.d(TAG,"Entered search fragment view")
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        SearchRecyclerList= arrayListOf<CategoryItemCard>()
        tempArrayList= arrayListOf<CategoryItemCard>()

        SearchRecyclerList.addAll(SplashActivity.allProductsList)
        mRecyclerView= binding.recyclerViewSearch
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter= CategoryAdapter(tempArrayList, this)
        SplashActivity.allProductsList= SplashActivity.allProductsList.distinct() as ArrayList<CategoryItemCard>
        SearchRecyclerList= SearchRecyclerList.distinct() as ArrayList<CategoryItemCard>
        SplashActivity.allProductsList.forEach {
            Log.d(TAG, "allItemList , ${it.Name}, ${it.Color}")
        }
        SearchRecyclerList.forEach {
            Log.d(TAG, "SearchRecyclerList , ${it.Name}, ${it.Color}")
        }

        //Citation: Adapted from Youtube tutorial - https://youtu.be/K5YnTvsVPRk
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d(TAG,"Inside onQuerySubmit")
                TODO("NOT YET IMPLEMENTED")

            }

            override fun onQueryTextChange(p1: String?): Boolean {
                Log.d(TAG,"Inside onQueryChange")
                tempArrayList.clear()
                val searchText=p1!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    Log.d(TAG,"Inside onQueryChange - if block ")
                    SearchRecyclerList.forEach{
                        Log.d(TAG,"Inside onQueryChange - if block - for each block")

                        if(it.Name.toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    mRecyclerView.adapter!!.notifyDataSetChanged()
                }
                else{
                    tempArrayList.clear()
                    tempArrayList.addAll(SearchRecyclerList)
                    mRecyclerView.adapter!!.notifyDataSetChanged()
                }

                return false
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


