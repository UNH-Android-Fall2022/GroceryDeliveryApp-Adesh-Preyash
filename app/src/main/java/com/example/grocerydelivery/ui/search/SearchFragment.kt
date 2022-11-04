package com.example.grocerydelivery.ui.search

import android.content.ContentValues.TAG
import com.example.grocerydelivery.ui.search.SearchViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grocerydelivery.databinding.FragmentSearchBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val db = Firebase.firestore

    //private var samples:MutableList<Sample> = arrayListOf()
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

        //val textView: TextView = binding.textNotifications
        //notificationsViewModel.text.observe(viewLifecycleOwner) {
        //  textView.text = it
        //}
        /*
        db.collection("sample").get().addOnSuccessListener { documents ->
            samples = mutableListOf()
            for (document in documents) {
                Log.d("GROCERY db data", "${document.id}=> ${document.data}")
                val sample = document.toObject(Sample::class.java)
                binding.textSearch.text= sample.info
                Log.d("GROCERY text output", sample.info)
                samples.add(sample)
            }
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents", exception)
                Log.d("GROCERY", "Error getting documents", exception)
            }

         */
        //binding.textSearch.text= samples[0].toString()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}