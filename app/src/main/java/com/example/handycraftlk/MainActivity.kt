package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
       getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

    fun buttonLogin(v: View){

        val intent = Intent(this, AboutUsPage::class.java)
        startActivity(intent)
        finish()
    }


}