package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.handycraftlk.R

class MainActivity_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_register)
    }

    fun buttonClick(v: View){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

