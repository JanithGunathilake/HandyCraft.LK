package com.example.handycraftlk

import SessionManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.handycraftlk.models.Product
import com.google.firebase.database.FirebaseDatabase


class SellerUpdateProduct : AppCompatActivity() {

    private lateinit var tvUpdateProName : TextView
    private lateinit var tvUpdateProPrice : TextView
    private lateinit var tvUpdateProDescription : TextView
    private lateinit var btnSellerUpdateProduct : Button
    private lateinit var btnSellerProductDelete : Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_update_product)
        setValuesToViews()


        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        btnSellerUpdateProduct = findViewById(R.id.btnSellerUpdateProduct)
        btnSellerProductDelete = findViewById(R.id.btnSellerProductDelete)

        btnSellerProductDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("productId").toString()
            )
        }


        btnSellerUpdateProduct.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("productId").toString(),
                intent.getStringExtra("proName").toString()
            )
        }



    }

    private fun deleteRecord(
        productId : String
    ){
        val dbref = FirebaseDatabase.getInstance().getReference("Product").child(productId)
        val mTask = dbref.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Product Data Deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SellerViewProduct::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Delete Err ${error.message}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun openUpdateDialog(
        productId : String,
        proName : String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.product_update_dialog, null)

        mDialog.setView(mDialogView)

        val edtProName = mDialogView.findViewById<EditText>(R.id.edtCategoryNam)
        val edtProPrice = mDialogView.findViewById<EditText>(R.id.edtProductPrice)
        val edtDescription = mDialogView.findViewById<EditText>(R.id.editProductDescription)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUp)

        edtProName.setText(intent.getStringExtra("proName").toString())
        edtProPrice.setText(intent.getStringExtra("proPrice").toString())
        edtDescription.setText(intent.getStringExtra("proDescription").toString())

        mDialog.setTitle("Updating $proName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()



        btnUpdate.setOnClickListener{
            updateProData(
                productId,
                edtProName.text.toString(),
                edtProPrice.text.toString(),
                edtDescription.text.toString()
            )
            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_SHORT).show()

            tvUpdateProName.text = edtProName.text.toString()
            tvUpdateProPrice.text = edtProPrice.text.toString()
            tvUpdateProDescription.text = edtDescription.text.toString()

            alertDialog.dismiss()

            val intent = Intent(this, SellerViewProduct::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun updateProData(
        productId: String,
        proName: String,
        proPrice: String,
        proDescription: String
    ){
        val dbref = FirebaseDatabase.getInstance().getReference("Product").child(productId)
        val proInfo = Product(proName,proPrice,proDescription,productId, sessionManager.getEmail())
        dbref.setValue(proInfo)
    }



    private fun setValuesToViews() {

        tvUpdateProName = findViewById(R.id.tvUpdateProName)
        tvUpdateProPrice = findViewById(R.id.tvUpdateProPrice)
        tvUpdateProDescription = findViewById(R.id.tvUpdateProDescription)

        intent.getStringExtra("productId")
        tvUpdateProName.text = intent.getStringExtra("proName")
        tvUpdateProPrice.text = intent.getStringExtra("proPrice")
        tvUpdateProDescription.text = intent.getStringExtra("proDescription")
    }



}