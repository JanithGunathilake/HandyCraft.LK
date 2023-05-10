package com.example.handycraftlk

import SessionManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.handycraftlk.databinding.ActivitySellerAddProductBinding
import com.example.handycraftlk.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SellerAddProduct : AppCompatActivity() {
    private lateinit var sessionManager : SessionManager//manage user sessions
    private lateinit var binding: ActivitySellerAddProductBinding//access to views
    private lateinit var database: DatabaseReference// used to store the new product data
    private lateinit var sellerEmail: String//variable to hold email address of current user

    //used to inflate the activity's layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //session manager initialization
        sessionManager = SessionManager(this)

        // check if the user is logged in; if not, redirect to the login page
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        // get the current user's email address
        sellerEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

        // get a reference to the "Product" node in the Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference("Product")

        // set up click listeners for the "Confirm", "Back", and "Cancel" buttons
        binding.btnConfirm.setOnClickListener{
            // get the product data entered by the user
            val proName = binding.edtProductName.text.toString()
            val proPrice = binding.edtPrice.text.toString()
            val proDescription = binding.edtDescription.text.toString()

            // Generate a unique ID for the product
            val productId = database.push().key

            //Creates a new Product object with the values entered by the user
            val product = Product(proName,proPrice,proDescription,productId, sellerEmail)
            if (productId != null) {
                //Sets the value of the Product object in the Firebase Realtime Database
                database.child(productId).setValue(product).addOnCompleteListener{

                    //clears the EditText views and displays a success message using a Toast
                    binding.edtProductName.text.clear()
                    binding.edtPrice.text.clear()
                    binding.edtDescription.text.clear()

                    Toast.makeText(this,"Successfully Added",Toast.LENGTH_SHORT).show()

                    //Starts a new activity to display the seller's products
                    val intent = Intent(this, SellerViewProduct::class.java)
                    startActivity(intent)
                    finish()

                    // triggered when the operation fails due to some error
                }.addOnFailureListener{

                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding.btnBackAddProduct.setOnClickListener{
            val intent = Intent(this, SellerDashboard::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCancelAddProduct.setOnClickListener{
            val intent = Intent(this, SellerDashboard::class.java)
            startActivity(intent)
            finish()
        }

    }
}
