package com.example.handycraftlk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.handycraftlk.databinding.ActivitySellerOrdersFragmentMainBinding
import com.example.handycraftlk.databinding.CheckoutCustomerBinding

class SellerOrdersFragmentMain : AppCompatActivity() {
    private lateinit var spending: TextView
    private lateinit var processing: TextView
    private lateinit var inDelivery: TextView
    private lateinit var complete: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_orders_fragment_main)
        spending=findViewById(R.id.sPending)
        processing=findViewById(R.id.sprocess)
        inDelivery=findViewById(R.id.sInDelivery)
       complete=findViewById(R.id.sComplete)


        //val fragmentCart = CartFragment()
        val sellerPendingOrderHistory= SellerPendingOrderHistory()
        val sellerProcessingOrderHistory=SellerProcessingOrderHistory()
        val sellerInDeliveryOrderHistroy=SellerInDeliveryOrderHistory()
        val sellerCompleteOrderHistory= SellerCompleteOrderHistory()
//        val customerCompleteHistory=CustomerCompleteHistory()


        complete.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,sellerCompleteOrderHistory)
                commit()            }
        }

        spending.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,sellerPendingOrderHistory)
                commit()            }
        }


        processing.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,sellerProcessingOrderHistory)
                commit()            }
        }

        inDelivery.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragview,sellerInDeliveryOrderHistroy)
                commit()            }
        }
    }
}