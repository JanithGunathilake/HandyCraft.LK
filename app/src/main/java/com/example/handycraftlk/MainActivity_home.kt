package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.handycraftlk.R

class MainActivity_home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
    }

    fun buttonClick(v: View){

        val intent = Intent(this, itemView::class.java)
        startActivity(intent)
        finish()
    }
}