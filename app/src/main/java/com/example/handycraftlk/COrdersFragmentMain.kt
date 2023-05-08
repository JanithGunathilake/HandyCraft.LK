package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class COrdersFragmentMain : AppCompatActivity() {
    private lateinit var spending: TextView
    private lateinit var processing: TextView
    private lateinit var inDelivery: TextView
    private lateinit var complete: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corders_fragment_main)
        spending=findViewById(R.id.cPending)
        processing=findViewById(R.id.processing)
        inDelivery=findViewById(R.id.cInDelivery)
        complete=findViewById(R.id.cComplete)


        //val fragmentCart = CartFragment()
        val customerPendingOrderHistory= CustomerPendingOrderHistory()
        val customerProcessOrderHistory=CustomerProcessOrderHistory()
        val customerInDeliveryHistory=CustomerInDeliveryHistory()
        val customerCompleteHistory=CustomerCompleteHistory()


        complete.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,customerCompleteHistory)
                commit()            }
        }

        spending.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,customerPendingOrderHistory)
                commit()            }
        }


        processing.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,customerProcessOrderHistory)
                commit()            }
        }

        inDelivery.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,customerInDeliveryHistory)
                commit()            }

        }
        val btnSellerBackImage = findViewById<ImageView>(R.id.imageView2)
        btnSellerBackImage.setOnClickListener { view ->
            btnBack(view)
        }
    }

    private fun btnBack(view: View) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}