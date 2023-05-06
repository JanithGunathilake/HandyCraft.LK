package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AboutUsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us_page)
    }
    fun btnGetStart(v: View){

        val intent = Intent(this, MainActivity_register::class.java)
        startActivity(intent)
        finish()
    }
}