package com.example.handycraftlk

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.handycraftlk.R

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imgHome: ImageView = findViewById(R.id.icnHome)
        val imgOrder: ImageView = findViewById(R.id.icnOrder)
        val imgCart: ImageView = findViewById(R.id.icnCart)
        val imgUser:ImageView = findViewById(R.id.icnUser)
        val fragmentHome = HomeFragment()
        val fragmentOrder = OrderFragment()
        val fragmentCart = CartFragment()
        val fragmentAccount = AccountFragment()

        imgOrder.setOnClickListener {
            imgHome.setImageResource(R.drawable.unselected_home)
            imgOrder.setImageResource(R.drawable.selected_order)
            imgCart.setImageResource(R.drawable.unselected_cart)
            imgUser.setImageResource(R.drawable.unselected_user)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragmentOrder)
                commit()
            }
        }





        imgCart.setOnClickListener {
            imgHome.setImageResource(R.drawable.unselected_home)
            imgOrder.setImageResource(R.drawable.unselected_order)
            imgCart.setImageResource(R.drawable.selected_cart)
            imgUser.setImageResource(R.drawable.unselected_user)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragmentCart)
                commit()
            }
        }

        imgUser.setOnClickListener {
            imgHome.setImageResource(R.drawable.unselected_home)
            imgOrder.setImageResource(R.drawable.unselected_order)
            imgCart.setImageResource(R.drawable.unselected_cart)
            imgUser.setImageResource(R.drawable.selected_user)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragmentAccount)
                commit()
            }
        }



        imgHome.setOnClickListener {
            imgHome.setImageResource(R.drawable.selected_home)
            imgOrder.setImageResource(R.drawable.unselected_order)
            imgCart.setImageResource(R.drawable.unselected_cart)
            imgUser.setImageResource(R.drawable.unselected_user)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragmentHome)
                commit()
            }
        }


    }
}