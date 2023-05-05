package com.example.handycraftlk

import SessionManager
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.handycraftlk.databinding.ActivitySellerProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SellerProfile : AppCompatActivity() {

    private lateinit var myButton: Button
    private lateinit var binding : ActivitySellerProfileBinding
    private lateinit var sessionManager : SessionManager
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        myButton = findViewById(R.id.btnSellerLogout)
        myButton.setOnClickListener { view ->
            sessionManager.logout()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }


        val userEmail = sessionManager.getEmail()
        database = FirebaseDatabase.getInstance().reference

        val userRef = database.child("Users")
        val query = userRef.orderByChild("email").equalTo(userEmail)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.value as HashMap<String, String>
                        val name = user["name"]
                        val email = user["email"]
                        val phoneNumber = user["phoneNumber"]
                        val id = user["id"]
                        val role = user["role"]
                        binding.tvSellerName.text = name
                        binding.tvSellerEmail.text = email


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read user details", error.toException())
            }
        })












        val btnAddProductImage = findViewById<ImageView>(R.id.btnAAdd)
        btnAddProductImage.setOnClickListener { view ->
            btnAddProduct(view)
        }
        val btnViewProductImage = findViewById<ImageView>(R.id.ivHomePL)
        btnViewProductImage.setOnClickListener { view ->
            btnViewProduct(view)
        }

        val btnSellerHomeImage = findViewById<ImageView>(R.id.btnSellerHome)
        btnSellerHomeImage.setOnClickListener { view ->
            btnSellerHome(view)
        }
        val btnSellerBackImage = findViewById<ImageView>(R.id.btnBackSellerProfile)
        btnSellerBackImage.setOnClickListener { view ->
            btnBack(view)
        }










    }
    private fun btnBack(view: View) {
        val intent = Intent(this, SellerDashboard::class.java)
        startActivity(intent)
        finish()
    }

    private fun btnAddProduct(view: View) {
        val intent = Intent(this, SellerAddProduct::class.java)
        startActivity(intent)
        finish()
    }

    private fun btnViewProduct(view: View) {
        val intent = Intent(this, SellerViewProduct::class.java)
        startActivity(intent)
        finish()
    }
    private fun btnSellerHome(view: View) {
        val intent = Intent(this, SellerDashboard::class.java)
        startActivity(intent)
        finish()
    }


}