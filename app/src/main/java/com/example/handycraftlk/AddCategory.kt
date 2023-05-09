package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.handycraftlk.databinding.ActivityAddCategoryBinding
import com.example.handycraftlk.models.Category
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCategory : AppCompatActivity() {

    private lateinit var binding : ActivityAddCategoryBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        //set the content view
        setContentView(binding.root)

        binding.btnAddCategory.setOnClickListener{
            val categoryName = binding.edtCategoryName.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Category")
            val categoryID = database.push().key // Generate a unique key using push() method
            ///if the id is not null create category object
            if (categoryID != null) {
                val category = Category(categoryID,categoryName )
                //set the value of the category object in the database
                database.child(categoryID).setValue(category).addOnCompleteListener{

                    binding.edtCategoryName.text.clear()
                    //toast message
                    Toast.makeText(this,"Successfully Added", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, AdminCategory::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{

                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}
