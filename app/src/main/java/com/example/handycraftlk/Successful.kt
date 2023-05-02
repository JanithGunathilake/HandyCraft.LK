package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Successful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful)
    }

    fun buttonClick(v: View){

        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }

}