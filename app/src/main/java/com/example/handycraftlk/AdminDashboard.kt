package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class AdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val btnTool = findViewById<ImageView>(R.id.ivTool)
        btnTool.setOnClickListener { view ->
            btnTools(view)
        }

        val btnProfile = findViewById<ImageView>(R.id.ivProfile)
        btnProfile.setOnClickListener { view ->
            btnProfile(view)
        }


    }

    private fun btnTools(view: View) {
        val intent = Intent(this, AdminTool::class.java)
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