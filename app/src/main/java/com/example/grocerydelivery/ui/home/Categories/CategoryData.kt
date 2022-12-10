package com.example.grocerydelivery.ui.home.Categories

data class CategoryData
    (
    //    val id: String ="",
//    var quantity: Int=0,
    var imageSrc: String="",
    var Name: String="",
    var Size: String="",
    var Color: String="",
    var Price: Double =0.0,
    var Uploaded: Boolean=false //Added Boolen "Uploaded" as flag variable to indicate product was uploaded or part of sample data
//    var productId: Int=0,
//    var description: String=""
)