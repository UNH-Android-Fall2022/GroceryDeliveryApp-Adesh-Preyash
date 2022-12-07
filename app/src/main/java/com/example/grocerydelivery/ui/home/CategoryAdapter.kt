//Citation: The entire file is referred to and adapted from live coding IceBreaker in class

package com.example.grocerydelivery.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.R
import com.example.grocerydelivery.ui.cart.CartCard
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.cartRecyclerList
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.cartSum
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.shipping
import com.example.grocerydelivery.ui.cart.CartFragment.Companion.taxes
import com.squareup.picasso.Picasso


class CategoryAdapter(

    private val mExampleList: ArrayList<CategoryItemCard>,
    private val context: Fragment
    ): RecyclerView.Adapter<CategoryAdapter.ExampleViewHolder>() {
    //Referred and adapted from IceBreaker live coding
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
        if(ImageSrc!="")
        {
            //Citation : referred to https://square.github.io/picasso/
            Picasso.get().load(ImageSrc).resize(100,100).into(holder.mImageView);
        }
        holder.mTextView1.text = Name
        holder.mTextView2.text = Size
        holder.mTextView3.text=Color
        holder.mTextView4.text= Price.toString()
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position clicked is $position")
        }
        holder.add_to_cart.setOnClickListener(object:View.OnClickListener {

            override fun onClick(v: View?) {
                if (v != null) {
                    v.setBackgroundColor(android.graphics.Color.parseColor("#808080"))
                    Toast.makeText(v.context,
                        "$Name  Added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("Added to cart",Name)
                cartRecyclerList.add(CartCard(ImageSrc,Name, Size, Color,Price))
                cartSum += Price.toDouble()
                taxes= cartSum*0.06
                shipping=5.0
            }
        })

    }
    override fun getItemCount(): Int {
        return mExampleList.size
    }
}
