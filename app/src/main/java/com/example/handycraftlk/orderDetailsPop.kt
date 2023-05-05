package com.example.handycraftlk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class orderDetailsPop : AppCompatActivity() {

    private lateinit var orderView:  TextView
    private lateinit var qunantityView: TextView
    private lateinit var addressView: TextView
    private lateinit var productView: TextView
    private lateinit var deleteBtn:Button
    private lateinit var update:Button

   // private lateinit var quantity : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details_pop)
        initView()
        setValuesToViews()
        deleteBtn = findViewById(R.id.deleteBtn)
        update=findViewById(R.id.updateBtn)

        update.setOnClickListener{
            updateStatus(
                intent.getStringExtra("orderId").toString()
            )
        }



        deleteBtn.setOnClickListener{
            deleterecord(
                intent.getStringExtra("orderId").toString()
            )
        }
    }


    private fun updateStatus(id:String){
        val dbref=FirebaseDatabase.getInstance().getReference("Order").child(id)

        val update = findViewById<Button>(R.id.updateBtn)
        update.setOnClickListener {
            val mTask=dbref.child("status").setValue("Processing")
            mTask.addOnSuccessListener {
                Toast.makeText(this,"Data Updated",Toast.LENGTH_SHORT).show()
                val intent=Intent(this,SellerPendingOrderHistory::class.java)
                finish()
                startActivity(intent)
            } .addOnFailureListener { error ->
                Toast.makeText(this,"Update error",Toast.LENGTH_SHORT).show()

            }
        }

    }


    @SuppressLint("SuspiciousIndentation")
    private  fun deleterecord(id:String){
        val dbref=FirebaseDatabase.getInstance().getReference("Order").child(id)
        val mTask=dbref.removeValue()
            mTask.addOnSuccessListener {
            Toast.makeText(this,"Data Deleted",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,SellerPendingOrderHistory::class.java)
            finish()
            startActivity(intent)
        } .addOnFailureListener { error ->
            Toast.makeText(this,"Delete error",Toast.LENGTH_SHORT).show()

        }



    }
    private  fun initView(){
        orderView=findViewById(R.id.orderView)
        qunantityView=findViewById(R.id.quantityView)
        productView=findViewById(R.id.productView)
        addressView=findViewById(R.id.adrressView)

    }
    private fun setValuesToViews(){
        orderView.text=intent.getStringExtra("orderId")
        qunantityView.text=intent.getStringExtra("quantityView")
        productView.text=intent.getStringExtra("productView")
        addressView.text=intent.getStringExtra("addressView")
       // quantity.text=intent.getStringExtra("quantity")
    }
}