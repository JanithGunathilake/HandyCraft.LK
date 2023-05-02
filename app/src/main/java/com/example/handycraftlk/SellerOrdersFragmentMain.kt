package com.example.handycraftlk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.handycraftlk.databinding.ActivitySellerOrdersFragmentMainBinding
import com.example.handycraftlk.databinding.CheckoutCustomerBinding

class SellerOrdersFragmentMain : AppCompatActivity() {

    lateinit var binding: ActivitySellerOrdersFragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySellerOrdersFragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sPending.setOnClickListener{
        replaceFragment(Spending())

        }
        binding.sApproved.setOnClickListener{
            replaceFragment(SApproved())

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager =supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()

    }
}