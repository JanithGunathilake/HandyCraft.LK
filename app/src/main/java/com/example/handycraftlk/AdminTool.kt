package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class AdminTool : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set the ui
        setContentView(R.layout.activity_admin_tool)
        //find the views by id
        val btnCategory = findViewById<TextView>(R.id.MCat)
        //when the view is clicked, calling the private methods
        btnCategory.setOnClickListener { view ->
            btnCategory(view)
        }

        val btnBackImage = findViewById<ImageView>(R.id.btnBackTool)
        btnBackImage.setOnClickListener{ view ->
            btnBack(view)
        }
        val btnHomeImage = findViewById<ImageView>(R.id.ivHomeTool)
        btnHomeImage.setOnClickListener{ view ->
            btnHome(view)
        }
        val btnProfile = findViewById<ImageView>(R.id.ivProfileTool)
        btnProfile.setOnClickListener{ view ->
            btnProfile(view)
        }
        val btnPL = findViewById<ImageView>(R.id.ivPL)
        btnPL.setOnClickListener{ view ->
            btnProductList(view)
        }


    }

    private fun btnCategory(view: View) {
        val intent = Intent(this, AdminCategory::class.java)
        startActivity(intent)
        finish()
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
    private fun btnProfile(view: View) {
        val intent = Intent(this, AdminProfile::class.java)
        startActivity(intent)
        finish()
    }
    private fun btnProductList(view: View) {
        val intent = Intent(this, AdminProductList::class.java)
        startActivity(intent)
        finish()
    }



    }



