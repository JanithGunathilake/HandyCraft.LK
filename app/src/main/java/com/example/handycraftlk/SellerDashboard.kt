package com.example.handycraftlk


import SessionManager
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.handycraftlk.databinding.ActivitySellerDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SellerDashboard : AppCompatActivity() {

    private lateinit var sessionManager : SessionManager
    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivitySellerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
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
                        binding.tvNameSeller.text = name



                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read user details", error.toException())
            }
        })




        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }




        val btnAddProductImage = findViewById<ImageView>(R.id.btnAdd)
        btnAddProductImage.setOnClickListener { view ->
            btnAddProduct(view)
        }
        val btnViewProductImage = findViewById<ImageView>(R.id.btnViewProduct)
        btnViewProductImage.setOnClickListener { view ->
            btnViewProduct(view)
        }
        val btnSellerProfileImage = findViewById<ImageView>(R.id.btnSellerProfile)
        btnSellerProfileImage.setOnClickListener{ view ->
            btnSellerProfile(view)
        }






    }
    private fun btnAddProduct(view: View) {
        val intent = Intent(this, SellerOrdersFragmentMain::class.java)
        startActivity(intent)
        finish()
    }

    private fun btnViewProduct(view: View) {
        val intent = Intent(this, SellerViewProduct::class.java)
        startActivity(intent)
        finish()
    }

    private fun btnSellerProfile(view: View) {
        val intent = Intent(this, SellerProfile::class.java)
        startActivity(intent)
        finish()
    }



}