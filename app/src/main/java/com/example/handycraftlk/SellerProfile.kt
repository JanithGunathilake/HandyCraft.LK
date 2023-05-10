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

    //inflates the layout XML file and returns a binding object.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //session manager is initialized and it checks if the user is already logged in
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


        //retrieve user's email from session manager
        val userEmail = sessionManager.getEmail()
        database = FirebaseDatabase.getInstance().reference

        //get a reference to the Firebase Realtime Database.
        val userRef = database.child("Users")
        //initialized with a query to search for the user with the given email.
        val query = userRef.orderByChild("email").equalTo(userEmail)

        //user's details are retrieved and displayed in the UI.
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










        //button launches a different activity when clicked


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
    //method launches a different activity using an Intent
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