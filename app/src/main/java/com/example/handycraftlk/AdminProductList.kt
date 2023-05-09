package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class AdminProductList : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set the layout
        setContentView(R.layout.activity_admin_product_list)

        //image views are defined
        val btnHomeImage = findViewById<ImageView>(R.id.ivHomePL)
        //when user click on any of images corresponding array will be executed
        btnHomeImage.setOnClickListener{ view ->
            btnHome(view)
        }
        val btnToolImage = findViewById<ImageView>(R.id.ivToolPL)
        btnToolImage.setOnClickListener{ view ->
            btnTool(view)
        }
        val btnProfileImage = findViewById<ImageView>(R.id.ivProfilePL)
        btnProfileImage.setOnClickListener{ view ->
            btnProfile(view)
        }




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
    private fun btnProfile(view: View) {
        val intent = Intent(this, AdminProfile::class.java)
        startActivity(intent)
        finish()
    }




}