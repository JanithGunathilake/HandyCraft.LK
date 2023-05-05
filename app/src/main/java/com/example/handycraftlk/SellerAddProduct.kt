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
    private lateinit var sessionManager : SessionManager
    private lateinit var binding: ActivitySellerAddProductBinding
    private lateinit var database: DatabaseReference
    private lateinit var sellerEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        sellerEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

        database = FirebaseDatabase.getInstance().getReference("Product")

        binding.btnConfirm.setOnClickListener{
            val proName = binding.edtProductName.text.toString()
            val proPrice = binding.edtPrice.text.toString()
            val proDescription = binding.edtDescription.text.toString()

            // Generate a unique ID for the product
            val productId = database.push().key

            val product = Product(proName,proPrice,proDescription,productId, sellerEmail)
            if (productId != null) {
                database.child(productId).setValue(product).addOnCompleteListener{

                    binding.edtProductName.text.clear()
                    binding.edtPrice.text.clear()
                    binding.edtDescription.text.clear()

                    Toast.makeText(this,"Successfully Added",Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, SellerViewProduct::class.java)
                    startActivity(intent)
                    finish()

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
