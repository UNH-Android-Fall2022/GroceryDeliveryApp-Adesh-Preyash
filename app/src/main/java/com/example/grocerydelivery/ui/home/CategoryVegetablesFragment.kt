package com.example.grocerydelivery.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocerydelivery.R

class CategoryVegetablesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryVegetablesFragment()
    }

    private lateinit var viewModel: CategoryVegetablesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_vegetables, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryVegetablesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}