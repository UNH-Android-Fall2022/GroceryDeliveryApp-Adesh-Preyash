package com.example.grocerydelivery.ui.home

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grocerydelivery.R
import com.example.grocerydelivery.activities.SuccessActivity
import com.example.grocerydelivery.ui.cart.CartCard
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.cartRecyclerList
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.cartSum
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.shipping
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.taxes
import com.squareup.picasso.Picasso
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.Double as Double


class CategoryAdapter(

    private val mExampleList: ArrayList<CategoryItemCard>,
    private val context: Fragment
    ): RecyclerView.Adapter<CategoryAdapter.ExampleViewHolder>() {

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView = itemView.findViewById (R.id.image_view)
        val mTextView1: TextView = itemView.findViewById (R.id.text_view_1)
        val mTextView2: TextView = itemView.findViewById (R.id.text_view_2)
        val mTextView3: TextView = itemView.findViewById (R.id.text_view_3)
        val mTextView4: TextView = itemView.findViewById (R.id.price)
        val add_to_cart: Button=itemView.findViewById(R.id.add_to_cart)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val (ImageSrc,Name, Size, Color, Price) = mExampleList[position]

        //Glide.with().load("http://goo.gl/gEgYUd").into(holder.mImageView);
        //val cartRecyclerList: ArrayList<CartCard> = ArrayList()
        if(ImageSrc!="")
        {
            Picasso.get().load(ImageSrc).into(holder.mImageView);
        }

        //TODO ("Implement Image Resource")
        holder.mTextView1.text = Name
        holder.mTextView2.text = Size
        holder.mTextView3.text=Color
        holder.mTextView4.text= Price.toString()
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position clicked is $position")
        }
        holder.add_to_cart.setOnClickListener{
            Log.d("Added to cart",Name)
            cartRecyclerList.add(CartCard(ImageSrc,Name, Size, Color,Price))

            cartSum += Price.toDouble()
            taxes= cartSum*0.06
            shipping=5.0


        }

    }



    override fun getItemCount(): Int {
        return mExampleList.size
    }
}
