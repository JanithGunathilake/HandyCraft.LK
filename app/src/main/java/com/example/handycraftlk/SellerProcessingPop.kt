package com.example.handycraftlk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.FirebaseDatabase

class SellerProcessingPop : AppCompatActivity() {

    private lateinit var orderView:  TextView
    private lateinit var quantityView: TextView
    private lateinit var addressView: TextView
    private lateinit var productView: TextView

    private lateinit var update: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_processing_pop)
        initView()
        setValuesToViews()

        update=findViewById(R.id.updateBtn)


        update.setOnClickListener{
            updateStatus(
                intent.getStringExtra("orderId").toString()
            )
        }

    }



    @SuppressLint("SuspiciousIndentation")
    private fun updateStatus(id:String){
        val dbref= FirebaseDatabase.getInstance().getReference("Order").child(id)

        val update = findViewById<Button>(R.id.updateBtn)
        update.setOnClickListener {
          val  mTask=dbref.child("status").setValue("In Delivery")
            mTask.addOnSuccessListener {
                Toast.makeText(this,"Data Updated",Toast.LENGTH_SHORT).show()
                val intent=Intent(this,SellerProcessingOrderHistory::class.java)
                finish()
                startActivity(intent)
            } .addOnFailureListener { error ->
                Toast.makeText(this,"Update error",Toast.LENGTH_SHORT).show()

            }
            }


        }

    private  fun initView(){
        orderView=findViewById(R.id.orderView)
        quantityView=findViewById(R.id.quantityView)
        productView=findViewById(R.id.productView)
        addressView=findViewById(R.id.adrressView)
    }
    private fun setValuesToViews(){
        orderView.text=intent.getStringExtra("orderId")
        quantityView.text=intent.getStringExtra("quantityView")
        productView.text=intent.getStringExtra("productView")
        addressView.text=intent.getStringExtra("addressView")
    }
    }


