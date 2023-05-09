package com.example.handycraftlk

import SessionManager
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.handycraftlk.adaptors.MyAdaptorM
import com.example.handycraftlk.databinding.ActivitySellerViewProductBinding
import com.example.handycraftlk.models.Product
import com.google.firebase.database.*

class SellerViewProduct : AppCompatActivity() {

    private lateinit var binding: ActivitySellerViewProductBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productArrayList : ArrayList<Product>
    private lateinit var sessionManager : SessionManager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerViewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //session
        //check if the user is logged in
        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }


        //productRecyclerView is initialized and set to use a LinearLayoutManager with fixed size
        productRecyclerView = findViewById(R.id.rvProductView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        //initializing
        productArrayList = arrayListOf<Product>()
        getProductData()



        //handle clicks on their respective buttons.
        val btnAddProduct = findViewById<Button>(R.id.btnAddProductView)
        btnAddProduct.setOnClickListener { view ->
            btnAddProduct(view)
        }

        val btnBack = findViewById<ImageView>(R.id.btnBackView)
        btnBack.setOnClickListener { view ->
            btnBack(view)
        }

    }

    // method navigates the user to the seller dashboard:
    private fun btnAddProduct(view: View) {
        val intent = Intent(this, SellerAddProduct::class.java)
        startActivity(intent)
        finish()
    }

    private fun btnBack(view: View) {
        val intent = Intent(this, SellerDashboard::class.java)
        startActivity(intent)
        finish()
    }

    //retrieve the product data from Firebase Realtime Database.
    private fun getProductData(){
        //gets the reference to the "Product" node in the Firebase Realtime Database.
        dbref = FirebaseDatabase.getInstance().getReference("Product")
        //retrieves the email address of the logged-in user
        val sellerEmail = sessionManager.getSession().get(SessionManager.KEY_EMAIL)

        //sorts the products by the sellerEmail field and retrieves only the products that belong to the logged-in seller.
       //database reference to retrieve the data
        dbref.orderByChild("sellerEmail").equalTo(sellerEmail).addValueEventListener(object : ValueEventListener {
            //listens for changes in the data and retrieves the data snapshot from the database
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    //loops through each child node of the snapshot and retrieves its value.
                    for (productSnapshot in snapshot.children){
                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    val myAdaptorM = MyAdaptorM(productArrayList)
                    // sets the MyAdaptorM instance as the RecyclerView adapter.
                    productRecyclerView.adapter = myAdaptorM
                    myAdaptorM.setOnItemClickListner(object : MyAdaptorM.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@SellerViewProduct,SellerUpdateProduct::class.java)

                            //extra data
                            //passes the data of the clicked item as extras to the SellerUpdateProduct activity.
                            intent.putExtra("proName", productArrayList[position].proName)
                            intent.putExtra("proPrice", productArrayList[position].proPrice)
                            intent.putExtra("proDescription", productArrayList[position].proDescription)
                            intent.putExtra("sellerEmail",productArrayList[position].sellerEmail)
                            intent.putExtra("productId", productArrayList[position].productId)
                            startActivity(intent)
                        }
                    })



                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }



}