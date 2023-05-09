package com.example.handycraftlk
import SessionManager
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.CartAdaptor
import com.example.handycraftlk.adaptors.MyAdapter
import com.example.handycraftlk.databinding.CheckoutCustomerBinding
import com.example.handycraftlk.models.Cart
import com.example.handycraftlk.models.Order
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomerCheckout : AppCompatActivity() {
    private lateinit var binding: CheckoutCustomerBinding
    private lateinit var  database:DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    //private lateinit var itemArrayList: ArrayList<Order>
    private lateinit var cartArrayList: ArrayList<Cart>
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = CheckoutCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get data


        itemRecyclerView=findViewById(R.id.checkoutitems)
        itemRecyclerView.layoutManager=LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)
        cartArrayList= arrayListOf<Cart>()
        getCartData()


      // insert

        binding.placeOrder.setOnClickListener(){

            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()


            var i= 0
            var j =cartArrayList.size
           while(i<j){


               val userEmail = sessionManager.getEmail()
            val address=binding.cusAddress.text.toString()
            val visaCard=binding.visaCard.text.toString()
            val cvv= binding.cvv.text.toString()
            val quantity="10"
            val totalAmount= cartArrayList[i].proPrice.toString()
            val email=userEmail.toString()
            val itemName="oooooo";

            val status="Pending"
                 val productId= cartArrayList[i].proName.toString()



            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dt=LocalDateTime.now().format(formatter).toString()
               val sellerId= cartArrayList[i].sellerEmail.toString()


            val orderId=database.push().key!!

            database= FirebaseDatabase.getInstance().getReference("Order")
            val Order=Order(address,visaCard,cvv, quantity,itemName,totalAmount,email,orderId,dt,status,sellerId,productId)
            database.child(orderId).setValue(Order).addOnSuccessListener {
                binding.visaCard.text.clear()
                binding.cvv.text.clear()
                binding.cusAddress.text.clear()
            }.addOnFailureListener{
                Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show()
            }

           i++}//end for
            Toast.makeText(this,"sucess message addded",Toast.LENGTH_SHORT).show()


        removeCartDataForUser()
        }
        val btnSellerBackImage = findViewById<ImageView>(R.id.backpage)
        btnSellerBackImage.setOnClickListener { view ->
            btnBack(view)
        }



    }
//    private fun getUserData(){
//        database=FirebaseDatabase.getInstance().getReference("Cart")
//        database.orderByChild("buyerEmail").equalTo("janithkavinda49@gmail.com")
//        database.addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for(itemSnapShot in snapshot.children){
//
//                        val item=itemSnapShot.getValue(Order::class.java)
//                        itemArrayList.add(item!!)
//
//
//                    }
//                    itemRecyclerView.adapter= MyAdapter(itemArrayList)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//
//    }


    private fun getCartData() {
        sessionManager = SessionManager(this)

         val userEmail = sessionManager.getEmail()
        database = FirebaseDatabase.getInstance().getReference("Cart")

        database.orderByChild("buyerEmail").equalTo(userEmail)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var totalPrice = 250.0
                        for (cartSnapshot in snapshot.children) {
                            val cart = cartSnapshot.getValue(Cart::class.java)
                            cartArrayList.add(cart!!)
                            totalPrice += cart.proPrice?.toDoubleOrNull() ?: 0.0
                        }
                        itemRecyclerView.adapter = CartAdaptor(cartArrayList)


                        val totalPriceTextView =findViewById<TextView>(R.id.chFinalTotal)
                        totalPriceTextView?.text = "$totalPrice"

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle error here
                }
            })
    }

    private fun btnBack(view: View) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    private fun removeCartDataForUser() {
        val userEmail = sessionManager.getEmail()
        database = FirebaseDatabase.getInstance().getReference("Cart")

        database.orderByChild("buyerEmail").equalTo(userEmail)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (cartSnapshot in snapshot.children) {
                            val cart = cartSnapshot.getValue(Cart::class.java)
                            if (cart?.buyerEmail == userEmail) {
                                cartSnapshot.ref.removeValue()
                            }
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error here
                }
            })
    }
}