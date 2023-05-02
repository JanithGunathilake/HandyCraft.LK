package com.example.handycraftlk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.handycraftlk.R

class itemView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_view)
    }
}