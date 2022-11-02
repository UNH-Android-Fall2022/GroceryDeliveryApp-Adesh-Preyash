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

class CartAdapter (

    private val mExampleList: ArrayList<CartCard>,
    private val context : CartFragment
    ) : RecyclerView.Adapter<CartAdapter.ExampleViewHolder>() {

        class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mImageView: ImageView = itemView.findViewById (R.id.image_view)
            val mTextView1: TextView = itemView.findViewById (R.id.text_view_1)
            val mTextView2: TextView = itemView.findViewById (R.id.text_view_2)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val (imageResource, text1, text2) = mExampleList[position]
        //TODO ("Implement Image Resource")
        holder.mTextView1.text = text1
        holder.mTextView2.text = text2

        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position clicked is $position")
        }
    }


    override fun getItemCount(): Int {
        return mExampleList.size
    }
}





/*
package com.example.pianifit_app.ui.workout

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pianifit_app.R

class WorkoutAdapter (
    private val mExampleList: ArrayList<WorkoutCard>,
    private val context : WorkoutFragment
        ) : RecyclerView.Adapter<WorkoutAdapter.ExampleViewHolder>() {

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView = itemView.findViewById (R.id.image_view)
        val mTextView1: TextView = itemView.findViewById (R.id.text_view_1)
        val mTextView2: TextView = itemView.findViewById (R.id.text_view_2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.saved_workout, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val (imageResource, text1, text2) = mExampleList[position]
        //TODO ("Implement Image Resource")
        holder.mTextView1.text = text1
        holder.mTextView2.text = text2

        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position clicked is $position")
        }
    }


    override fun getItemCount(): Int {
        return mExampleList.size
    }
}
 */