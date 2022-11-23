package com.example.grocerydelivery.ui.cart

import android.os.Binder
import com.example.grocerydelivery.ui.cart.CartViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    companion object{
        val cartRecyclerList: ArrayList<CartCard> = ArrayList()
        var cartSum : Double = 0.0
        var shipping : Double=0.0
        var taxes: Double=0.0

    }

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
//        for(cartItem in cartList){
//            cartRecyclerList.add(
//                CartCard(
//                    cartItem.image,
//                    cartItem.name,
//                    cartItem.quantity.toString()+"Units"
//                )
//            )
//        }

        mRecyclerView= binding.recylerViewCart
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = CartAdapter(cartRecyclerList, this)

        val mCartTotal=binding.cartTotalAmount
        mCartTotal.text= cartSum.toString()

        val mShippingCost=binding.shippingCost
        mShippingCost.text= shipping.toString()

        val mTaxes=binding.taxesAmount
        mTaxes.text= taxes.toString()

        val mTotalAmount=binding.totalAmount
        val total = cartSum+ shipping+taxes
        mTotalAmount.text= total.toString()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}