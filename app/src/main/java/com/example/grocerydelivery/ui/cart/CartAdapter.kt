//Citation: The entire file is referred to and adapted from live coding IceBreaker in class

package com.example.grocerydelivery.ui.cart

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.R
import com.squareup.picasso.Picasso

class CartAdapter (

    private val mExampleList: ArrayList<CartCard>,
    private val context : CartFragment
    ) : RecyclerView.Adapter<CartAdapter.ExampleViewHolder>() {

        class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mImageView: ImageView = itemView.findViewById (R.id.image_view)
            val mTextView1: TextView = itemView.findViewById (R.id.text_view_1)
            val mTextView2: TextView = itemView.findViewById (R.id.text_view_2)
            val mTextView3: TextView = itemView.findViewById (R.id.text_view_3)
            val mTextView4: TextView = itemView.findViewById (R.id.price)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val (ImageSrc, Name, Size, Color, Price) = mExampleList[position]
        //TODO ("Implement Image Resource")

        if(ImageSrc!="")
        {
            //Citation : referred to https://square.github.io/picasso/
            Picasso.get().load(ImageSrc).into(holder.mImageView);
        }

        //TODO ("Implement Image Resource")
        holder.mTextView1.text = Name
        holder.mTextView2.text = Size
        holder.mTextView3.text=Color
        holder.mTextView4.text= "$"+Price.toString()

    }


    override fun getItemCount(): Int {
        return mExampleList.size
    }
}
