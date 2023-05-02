package com.example.handycraftlk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.databinding.CheckoutCustomerBinding
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomerCheckout : AppCompatActivity() {
    private lateinit var binding: CheckoutCustomerBinding
    private lateinit var  database:DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Order>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = CheckoutCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get data

        itemRecyclerView=findViewById(R.id.checkoutitems)
        itemRecyclerView.layoutManager=LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)
        itemArrayList= arrayListOf<Order>()
        getUserData()


        //insert
        binding.placeOrder.setOnClickListener(){

            val address=binding.cusAddress.text.toString()
            val visaCard=binding.visaCard.text.toString()
            val cvv= binding.cvv.text.toString()
            val quantity="10";
            val totalAmount="250";
            val itemName="oooooo";
            val custId="123";
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            val dt=LocalDateTime.now().format(formatter).toString()
            val orderId=database.push().key!!

            database= FirebaseDatabase.getInstance().getReference("Order")
            val Order=Order(address,visaCard,cvv, quantity,itemName,totalAmount,custId,orderId,dt)
            database.child(orderId).setValue(Order).addOnSuccessListener {
                binding.visaCard.text.clear()
                binding.cvv.text.clear()
                binding.cusAddress.text.clear()
                Toast.makeText(this,"sucess",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show()
            }

        }




    }
    private fun getUserData(){
        database=FirebaseDatabase.getInstance().getReference("Order")
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapShot in snapshot.children){

                        val item=itemSnapShot.getValue(Order::class.java)
                        itemArrayList.add(item!!)
                    }
                    itemRecyclerView.adapter=MyAdapter(itemArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}