package com.example.handycraftlk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.handycraftlk.databinding.ActivityMainRegisterBinding
import com.example.handycraftlk.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity_register : AppCompatActivity() {

    private lateinit var binding: ActivityMainRegisterBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityMainRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner = findViewById<Spinner>(R.id.spinner)
        auth = Firebase.auth

        binding.btnCreateAcc.setOnClickListener {

            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val phoneNumber = binding.edtPhone.text.toString()
            val password = binding.edtPwd.text.toString()
            val rePassword = binding.edtRePwd.text.toString()

            if (!isValidName(name)) {
                binding.edtName.error = "Invalid name"
            } else if (!isValidEmail(email)) {
                binding.edtEmail.error = "Invalid email"
            } else if (phoneNumber.isEmpty()) {
                binding.edtPhone.error = "Phone Number is Empty"
            } else if (!isValidPassword(password)) {
                binding.edtPwd.error = "Password is Invalid"
            } else if (rePassword.isEmpty()) {
                binding.edtRePwd.error = "Password is Empty"
            } else if (password != rePassword) {
                binding.edtRePwd.error = "Password not Matching"
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // User registered successfully in Firebase Authentication
                            val user = auth.currentUser
                            val Id = user!!.uid
                            val userData = User(name, email, phoneNumber, password, rePassword, spinner.selectedItem.toString(), Id)

                            database = FirebaseDatabase.getInstance().getReference("Users")
                            database.child(Id).setValue(userData).addOnSuccessListener {
                                binding.edtName.text.clear()
                                binding.edtEmail.text.clear()
                                binding.edtPhone.text.clear()
                                binding.edtPwd.text.clear()
                                binding.edtRePwd.text.clear()

                                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, Successful::class.java)
                                finish()
                                startActivity(intent)
                            }.addOnFailureListener {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // User registration failed
                            Toast.makeText(baseContext, "Registration failed, please try again later.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidName(name: String): Boolean {
        return name.isNotEmpty() && name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex())
    }

        private fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\$@!%*?&])[A-Za-z\\d\$@!%*?&]{8,}$".toRegex()
        return password.isNotEmpty() && password.matches(pattern)
    }
    fun buttonLogin(v: View){

        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }
}
