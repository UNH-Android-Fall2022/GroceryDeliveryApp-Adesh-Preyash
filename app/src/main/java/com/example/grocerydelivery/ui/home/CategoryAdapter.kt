package com.example.grocerydelivery.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerydelivery.R

class CategoryAdapter(
    private val mExampleList: ArrayList<CategoryItemCard>,
    private val context: CategoryFruitsFragment
    ): RecyclerView.Adapter<CategoryAdapter.ExampleViewHolder>() {

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView = itemView.findViewById (R.id.image_view)
        val mTextView1: TextView = itemView.findViewById (R.id.text_view_1)
        val mTextView2: TextView = itemView.findViewById (R.id.text_view_2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val (Name, Size, Color) = mExampleList[position]
        //TODO ("Implement Image Resource")
        holder.mTextView1.text = Name
        holder.mTextView2.text = Size

        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position clicked is $position")
        }
    }


    override fun getItemCount(): Int {
        return mExampleList.size
    }
}
