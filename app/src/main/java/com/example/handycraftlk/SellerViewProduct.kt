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
        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }


        productRecyclerView = findViewById(R.id.rvProductView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        productArrayList = arrayListOf<Product>()
        getProductData()



        val btnAddProduct = findViewById<Button>(R.id.btnAddProductView)
        btnAddProduct.setOnClickListener { view ->
            btnAddProduct(view)
        }

        val btnBack = findViewById<ImageView>(R.id.btnBackView)
        btnBack.setOnClickListener { view ->
            btnBack(view)
        }

    }

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

    private fun getProductData(){
        dbref = FirebaseDatabase.getInstance().getReference("Product")
        val sellerEmail = sessionManager.getSession().get(SessionManager.KEY_EMAIL)

        dbref.orderByChild("sellerEmail").equalTo(sellerEmail).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (productSnapshot in snapshot.children){
                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    val myAdaptorM = MyAdaptorM(productArrayList)
                    productRecyclerView.adapter = myAdaptorM
                    myAdaptorM.setOnItemClickListner(object : MyAdaptorM.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@SellerViewProduct,SellerUpdateProduct::class.java)

                            //extra data
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