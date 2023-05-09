package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class AdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set the layout
        setContentView(R.layout.activity_admin_dashboard)
        //find the imageview object
        val btnTool = findViewById<ImageView>(R.id.ivTool)
        //set the click listener for the profile
        btnTool.setOnClickListener { view ->
            btnTools(view)
        }

        val btnProfile = findViewById<ImageView>(R.id.ivProfile)
        btnProfile.setOnClickListener { view ->
            btnProfile(view)
        }
        val btnPL = findViewById<ImageView>(R.id.ivProductView)
        btnPL.setOnClickListener { view ->
            btnProductList(view)
        }

    }

    private fun btnTools(view: View) {
        val intent = Intent(this, AdminTool::class.java)
        startActivity(intent)
        //finish the current activity
        finish()
    }

    private fun btnProfile(view: View) {
        val intent = Intent(this, AdminProfile::class.java)
        //startActivity
        startActivity(intent)
        finish()
    }
    private fun btnProductList(view: View) {
        val intent = Intent(this, AdminProductList::class.java)
        startActivity(intent)
        finish()
    }
}