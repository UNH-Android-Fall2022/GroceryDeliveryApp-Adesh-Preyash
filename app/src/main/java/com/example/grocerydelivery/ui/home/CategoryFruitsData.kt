package com.example.grocerydelivery.ui.home

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



private var fruits_list:ArrayList<CategoryFruitsData>  = arrayListOf()
private var mAuth: FirebaseAuth? = null
//private val db = Firebase.firestore

data class CategoryFruitsData(

//    val id: String ="",
//    var quantity: Int=0,
//    var image: String="",
     var Name: String="",
    var Size: String="",
    var Color: String="",
//    var productId: Int=0,
//    var description: String=""
)


var CategoryFruitsList: ArrayList<CategoryFruitsData> = arrayListOf(

    CategoryFruitsData("Strawberry","Small","Red"),
    CategoryFruitsData("Banana","Medium","Yellow"),
    CategoryFruitsData("Grapes","Small","Black"),
    CategoryFruitsData("Raspberries","Small","Maroon"),
    CategoryFruitsData("Plum","Small","Dark Red"),
    //CategoryFruitsData(fruits_list[1].Name, fruits_list[1].Size, fruits_list[1].Color)
)
/*fun getFruitsFromDb()
{
    db.collection("fruits")
        .get()
        .addOnSuccessListener { documents ->
            fruits_list= ArrayList()
            for(document in documents){
                Log.d(ContentValues.TAG, "${document.id}=> ${document.data}")

                val fruit=document.toObject(CategoryFruitsData::class.java)
                //Log.d(TAG, fruit.id)
                fruits_list.add(fruit)
                Log.d("Fruit db check",fruit.Name)
                Log.d("Fruit db check",fruit.Size)
                Log.d("Fruit db check",fruit.Color)


            }
        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents", exception)
        }


}*/
