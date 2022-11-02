package com.example.grocerydelivery.ui.cart

data class CartData (

    val id: String ="",
    var quantity: Int=2,
    var image: String="",
    var name: String="",
    var productId: Int=0,
    var description: String=""

)

val cartList: ArrayList<CartData> = arrayListOf(
    CartData("",1,"","Fruit 1",1,""),
    CartData("",2,"","Vegetable 1",1,""),
    CartData("",3,"","Cereals",1,""),
    CartData("",1,"","Fruit 1",1,""),
    CartData("",1,"","Fruit 1",1,""),
    CartData("",1,"","Fruit 1",1,"")
)


/*

data class WorkoutData (
    val id: String ="",
    var workoutTime: Int=10, //minutes
    var image: String="",
    var name: String="",
    var workoutType: Int=0,
    var workoutEquipment: Int=0,
    var workoutPlanId: String=""
)
//TODO : Use firebase to query instead of hardcoded data
val workoutList: ArrayList<WorkoutData> = arrayListOf(
    WorkoutData("", 20,"","Workout 1",0,0,""),
    WorkoutData("", 20,"","Workout 2",0,0,""),
    WorkoutData("", 20,"","Workout 3",0,0,""),
    WorkoutData("", 20,"","Workout 4",0,0,""),
    WorkoutData("", 20,"","Workout 5",0,0,""),
    WorkoutData("", 20,"","Workout 6",0,0,""),
    WorkoutData("", 20,"","Workout 7",0,0,""),
    WorkoutData("", 20,"","Workout 8",0,0,""),
)

 */