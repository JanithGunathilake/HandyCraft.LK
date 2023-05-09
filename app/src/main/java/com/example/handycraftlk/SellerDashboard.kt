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

    //declaring variables
    private lateinit var sessionManager : SessionManager
    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivitySellerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflates the layout XML file and returns a binding object
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing session manager
        sessionManager = SessionManager(this)

        // checks if the user is already logged in
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }


        //retrieving user's email from the session manager
        val userEmail = sessionManager.getEmail()
        //get a reference to the Firebase Realtime Database.
        database = FirebaseDatabase.getInstance().reference

        // reference to the Users node in the database.
        val userRef = database.child("Users")
        //initialized with a query to search for the user with the given email
        val query = userRef.orderByChild("email").equalTo(userEmail)

        // retrieves the user's details if the query returns a result
        query.addListenerForSingleValueEvent(object : ValueEventListener {
           //user's details are retrieved and displayed in the UI.
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

            //error message is logged
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




        //sets up click listeners for Each button
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
    //when the corresponding button is clicked it loads different activity
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