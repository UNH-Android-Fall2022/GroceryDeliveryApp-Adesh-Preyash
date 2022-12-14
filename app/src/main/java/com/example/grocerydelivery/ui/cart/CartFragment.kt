//Citation: The entire file is referred to and adapted from live coding IceBreaker in class

package com.example.grocerydelivery.ui.cart

import android.app.Activity
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowInsets
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.R
import com.example.grocerydelivery.activities.CheckoutActivity
import com.example.grocerydelivery.databinding.FragmentCartBinding
import com.example.grocerydelivery.ui.home.HomeFragmentDirections


class CartFragment : Fragment() {

    companion object{
        val cartRecyclerList: ArrayList<CartCard> = ArrayList()
        var cartSum : Double = 0.0
        var shipping : Double=0.0
        var taxes: Double=0.0
        var total: Double=0.0

    }

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val checkoutButton =binding.checkoutBtn
        checkoutButton.setOnClickListener{
            Toast.makeText(
                activity,
                "Going to checkout page",
                Toast.LENGTH_SHORT
            ).show()
            checkout_page()
        }

        mRecyclerView= binding.recylerViewCart
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = CartAdapter(cartRecyclerList, this)

        //Citation : https://www.folkstalk.com/2022/09/how-to-limit-decimal-in-double-kotlin-with-code-examples.html
        cartSum=String.format("%.2f", cartSum).toDouble()
        shipping=String.format("%.2f", shipping).toDouble()
        taxes=String.format("%.2f", taxes).toDouble()

        val mCartTotal=binding.cartTotalAmount
        mCartTotal.text= "$"+cartSum.toString()

        val mShippingCost=binding.shippingCost
        mShippingCost.text= "$"+shipping.toString()

        val mTaxes=binding.taxesAmount
        mTaxes.text= "$"+taxes.toString()

        val mTotalAmount=binding.totalAmount
        total = cartSum+ shipping+taxes
        total=String.format("%.2f", total).toDouble()
        mTotalAmount.text= "$"+total.toString()

        return root
    }
    private fun checkout_page() {
        Log.d(TAG,"Checkout btn clicked")
        val action= CartFragmentDirections.actionNavigationCartToCheckoutActivity()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}