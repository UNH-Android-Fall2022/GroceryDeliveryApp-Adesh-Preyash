package com.example.grocerydelivery.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.grocerydelivery.R
import com.example.grocerydelivery.utils.GSButton
import com.example.grocerydelivery.utils.GSEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.File

// Citation :  https://www.tutorialkart.com/kotlin-android/android-spinner-kotlin-example/
class ProductUploadActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    // Citation :  https://github.com/firebase/snippets-android/blob/6884c3f19860bb2010677680f7324e8f7dd15a56/storage/app/src/main/java/com/google/firebase/referencecode/storage/kotlin/StorageActivity.kt#L189-L195
    // [START storage_field_declaration]
    lateinit var storage: FirebaseStorage
    // [END storage_field_declaration]

    private  lateinit var product_view: ImageView

    // Citation :  https://developer.android.com/guide/topics/resources/string-resource
    var categories_list = arrayOf("Fruits","Vegetables","Dairy","Breads","Cosmetics","Cleaning")
    var sizes_list = arrayOf("Small","Medium","Large")

    lateinit var category_selected : String
    lateinit var size_selected : String
    lateinit var productName : GSEditText
    lateinit var productColor : GSEditText
    lateinit var productPrice : GSEditText

    private val TAG="GroceryAndroidDebug"
    lateinit var imageForProduct : String
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_upload)

        // Citation : https://firebase.google.com/docs/storage/android/start#set-up-cloud-storage
        storage = Firebase.storage


        // Citation :  https://www.tutorialkart.com/kotlin-android/android-spinner-kotlin-example/
        // Create an ArrayAdapter using a simple spinner layout and languages array
        //val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories_list)
        val categoryAdapter = ArrayAdapter.createFromResource(this,R.array.categories,R.layout.spinner_item)
        // Set layout to use when the list of choices appear
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        val category_spinner=findViewById<Spinner>(R.id.category_spinner)
        category_spinner!!.setAdapter(categoryAdapter)
        category_spinner!!.setOnItemSelectedListener(this)
        val sizeAdapter=ArrayAdapter.createFromResource(this,R.array.sizes,R.layout.spinner_item)
        // Set layout to use when the list of choices appear
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        val size_spinner=findViewById<Spinner>(R.id.size_spinner)
        size_spinner!!.setAdapter(sizeAdapter)
        size_spinner!!.setOnItemSelectedListener(this)



        productName = findViewById<GSEditText>(R.id.product_name)
        productColor = findViewById<GSEditText>(R.id.product_color)
        productPrice=findViewById<GSEditText>(R.id.product_price)




        product_view=findViewById<ImageView>(R.id.product_image)
        val uploadImageBtn=findViewById<GSButton>(R.id.btn_upload_image)

        // Citation :  https://stackoverflow.com/questions/49697630/open-the-file-chooser-in-an-android-app-using-kotlin
        uploadImageBtn.setOnClickListener {
            Log.d(TAG, "Upload Image button clicked")
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }

        //Submit product button
        val submitProductBtn = findViewById<GSButton>(R.id.submitProduct)
        submitProductBtn.setOnClickListener{
            Log.d(TAG, "Submit button clicked")
            writeProductToDatabase()


        }
    }

    //Citation :Auto implement by Android Studio
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO("Not yet implemented")
        // Citation :  https://www.tutorialkart.com/kotlin-android/android-spinner-kotlin-example/

        //Looking for which spinner was used
        if (parent != null) {
            if (parent.id==R.id.size_spinner)
            {
                size_selected= sizes_list[position].toString()
            }
        }
        if (parent != null) {
            if(parent.id==R.id.category_spinner)
            {
                category_selected= categories_list[position].toString()
            }
        }

    }

    // Citation : Auto implement by Android Studio
    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }


    // Citation :  https://stackoverflow.com/questions/49697630/open-the-file-chooser-in-an-android-app-using-kotlin
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data // The URI with the location of the file
            //Setting image viw as picture uploaded
            product_view.setImageURI(data?.data)

            val b = data?.data!!
            Log.d(TAG, data?.data!!.toString())
            imageForProduct=data?.data!!.toString()

        }
    }

    // Writing product to Firebase
    fun writeProductToDatabase()
    {
        Log.d(TAG, "Inside WriteToDatabase function")

        uploadImage()
        val prod_name=productName.text.toString()
        val prod_color=productColor.text.toString()
        val prod_price=productPrice.text.toString().toDouble()

        //Creating hashmap for writing to Firebase
        val product= hashMapOf(
            "Name" to prod_name,
            "Color" to prod_color,
            "imageSrc" to imageForProduct,
            "Price" to prod_price,
            "Type" to category_selected,
            "Size" to size_selected,
            "Uploaded" to true
        )
        category_selected=category_selected.lowercase()
        //Writing order_details to Firebase
        db.collection("inventory")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(baseContext, "Product Submitted Successfully",
                    Toast.LENGTH_SHORT).show()

                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }

    fun uploadImage()
    {
        Log.d(TAG, "Inside uploadImage function")

        // Citation : https://firebase.google.com/docs/storage/android/create-reference#create_a_reference
        // Create a storage reference from our app
        val storageRef = storage.reference

        val prod_name=productName.text.toString()

        // Citation : https://firebase.google.com/docs/storage/android/upload-files#upload_files
        var uploadTask: UploadTask?
        var file = Uri.fromFile(File("path/to/${prod_name}.jpg"))
        val riversRef = storageRef.child("${file.lastPathSegment}")
        uploadTask = riversRef.putFile(Uri.parse(imageForProduct))
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(baseContext, "Image Upload Failed",
                Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...

            Toast.makeText(baseContext, "Image Uploaded Successfully",
                Toast.LENGTH_SHORT).show()

        }
    }
}