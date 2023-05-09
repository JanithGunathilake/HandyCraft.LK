package com.example.handycraftlk

import SessionManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.handycraftlk.databinding.ActivitySingleProductViewBinding
import com.google.firebase.database.FirebaseDatabase

class SingleProductView : AppCompatActivity() {
    private lateinit var tvProName : TextView
    private lateinit var tvProPrice : TextView
    private lateinit var tvProDescription : TextView
    private lateinit var sessionManager : SessionManager
    private lateinit var btnAddToCart : Button
    private lateinit var binding : ActivitySingleProductViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hides the action bar of the activity.
        getSupportActionBar()?.hide()


        //session
        //Initializes the sessionManager property
        sessionManager = SessionManager(this)

        // Checks if the user is logged in
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        //Initializes the property using the findViewById method.
        tvProName = findViewById(R.id.tvProName)
        tvProPrice = findViewById(R.id.tvProPrice)
        tvProDescription = findViewById(R.id.tvProDescription)
        btnAddToCart = findViewById(R.id.btnAddToCart)

        setValuesToViews()
        //product adding to cart
        btnAddToCart.setOnClickListener {
            val productRef = FirebaseDatabase.getInstance().getReference("Product").push()
            val cartRef = FirebaseDatabase.getInstance().getReference("Cart").child(sessionManager.getSession().get("id")
                .toString()).child(productRef.key ?: "")

//            val productData = HashMap<String, Any>()
//            productData["proName"] = intent.getStringExtra("proName") ?: ""
//            productData["proPrice"] = intent.getStringExtra("proPrice") ?: ""
//            productData["proDescription"] = intent.getStringExtra("proDescription") ?: ""
//            productData["sellerEmail"] = intent.getStringExtra("sellerEmail") ?: ""

            //productRef.setValue(productData)

            val cartData = HashMap<String, Any>()
            cartData["productId"] = productRef.key ?: ""
            cartData["buyerEmail"] = sessionManager.getSession().get("email") ?: ""
            cartData["sellerEmail"] = intent.getStringExtra("sellerEmail") ?: ""
            cartData["productId"] = intent.getStringExtra("productId") ?: ""
            cartData["proPrice"] = intent.getStringExtra("proPrice") ?: ""
            cartData["proName"] = intent.getStringExtra("proName") ?: ""

            cartRef.setValue(cartData)
            Toast.makeText(this, "Item Added to Cart", Toast.LENGTH_SHORT).show()

            finish()
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
    private fun setValuesToViews(){
        tvProName.text = intent.getStringExtra("proName")
        tvProPrice.text = intent.getStringExtra("proPrice")
        tvProDescription.text = intent.getStringExtra("proDescription")
    }

}
