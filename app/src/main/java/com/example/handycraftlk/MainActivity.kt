package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.handycraftlk.databinding.ActivityAccInfomationBinding
import com.example.handycraftlk.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

    fun buttonLogin(v: View){

        val intent = Intent(this, MainActivity_register::class.java)
        startActivity(intent)
        finish()
    }


}