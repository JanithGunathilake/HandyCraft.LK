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
import com.example.handycraftlk.databinding.ActivityAdminProfileBinding
import com.example.handycraftlk.databinding.ActivitySellerProfileBinding
import com.google.firebase.database.*

class AdminProfile : AppCompatActivity() {

    private lateinit var myButton: Button
    private lateinit var binding : ActivityAdminProfileBinding
    private lateinit var sessionManager : SessionManager
    private lateinit var database : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        myButton = findViewById(R.id.btnAdminLogout)
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

                        binding.tvAdminEmail.text = email
                        binding.tvAdminName.text = name
                        binding.tvAdminPhone.text = phoneNumber


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read user details", error.toException())
            }
        })









        val btnBackImage = findViewById<ImageView>(R.id.btnBackProfile)
        btnBackImage.setOnClickListener{ view ->
            btnBack(view)
        }

        val btnHomeImage = findViewById<ImageView>(R.id.ivHomeProfile)
        btnHomeImage.setOnClickListener{ view ->
            btnHome(view)
        }

        val btnToolImage = findViewById<ImageView>(R.id.ivToolProfile)
        btnToolImage.setOnClickListener{ view ->
            btnTool(view)
        }
        val btnPL = findViewById<ImageView>(R.id.ivProList)
        btnPL.setOnClickListener{ view ->
            btnProductList(view)
        }



    }

    private fun btnBack(view: View) {
        val intent = Intent(this, AdminDashboard::class.java)
        startActivity(intent)
        finish()
    }
    private fun btnHome(view: View) {
        val intent = Intent(this, AdminDashboard::class.java)
        startActivity(intent)
        finish()
    }
    private fun btnTool(view: View) {
        val intent = Intent(this, AdminTool::class.java)
        startActivity(intent)
        finish()
    }
    private fun btnProductList(view: View) {
        val intent = Intent(this, AdminProductList::class.java)
        startActivity(intent)
        finish()
    }


}