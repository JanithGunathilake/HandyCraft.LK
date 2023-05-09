package com.example.handycraftlk

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handycraftlk.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginPage : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    //Declare variable
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        //Create a click listener for the login button binding.btnLogin
        binding.btnLogin.setOnClickListener {


            val email = binding.edtEmailLog.text.toString()
            val password = binding.edtPwdLog.text.toString()



            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userId = user?.uid
                            if (userId != null) {
                                database.child("Users").child(userId).child("role").get()
                                    .addOnSuccessListener {
                                            dataSnapshot ->
                                        val role = dataSnapshot.value as String?
                                        if (role == "Seller") {
                                            val intent = Intent(this, SellerDashboard::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else if(role == "Buyer") {
                                            val intent = Intent(this, Home::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else if(role == "Admin") {
                                            val intent = Intent(this, AdminDashboard::class.java)
                                            startActivity(intent)
                                            finish()
                                         }
                                        else {
                                            Toast.makeText(this, "Failed to get user role:", Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(this, "Failed to get user role: ${exception.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                sessionManager.saveSession(email, password)
            }
        }


        binding.tvCreateAcc.setOnClickListener{
            val intent = Intent(this, MainActivity_register::class.java)
            startActivity(intent)
        }


    }
}
