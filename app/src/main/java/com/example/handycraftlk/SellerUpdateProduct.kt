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

    //Define variables for the view elements:
    private lateinit var tvUpdateProName : TextView
    private lateinit var tvUpdateProPrice : TextView
    private lateinit var tvUpdateProDescription : TextView
    private lateinit var btnSellerUpdateProduct : Button
    private lateinit var btnSellerProductDelete : Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_update_product)
        //set the values of the different view elements
        setValuesToViews()


        // Check if user is logged in
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

    // used to delete a product record from the database when the delete button is clicked
    private fun deleteRecord(
        productId : String
    ){
        // gets the reference to the Product node in the database
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

    //opens a dialog box to allow the user to update the product details
    private fun openUpdateDialog(
        productId : String,
        proName : String
    ){
        val mDialog = AlertDialog.Builder(this)
        //layout for the update dialog is inflated
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.product_update_dialog, null)

        mDialog.setView(mDialogView)

        val edtProName = mDialogView.findViewById<EditText>(R.id.edtCategoryNam)
        val edtProPrice = mDialogView.findViewById<EditText>(R.id.edtProductPrice)
        val edtDescription = mDialogView.findViewById<EditText>(R.id.editProductDescription)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUp)

        //setting values
        edtProName.setText(intent.getStringExtra("proName").toString())
        edtProPrice.setText(intent.getStringExtra("proPrice").toString())
        edtDescription.setText(intent.getStringExtra("proDescription").toString())

        mDialog.setTitle("Updating $proName Record")

        //An AlertDialog object is created
        val alertDialog = mDialog.create()
        alertDialog.show()



        // takes the updated values
        btnUpdate.setOnClickListener{
            updateProData(
                productId,
                edtProName.text.toString(),
                edtProPrice.text.toString(),
                edtDescription.text.toString()
            )
            Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_SHORT).show()

            //updated with the new values using the setText() function.
            tvUpdateProName.text = edtProName.text.toString()
            tvUpdateProPrice.text = edtProPrice.text.toString()
            tvUpdateProDescription.text = edtDescription.text.toString()

            //dialog is dismissed
            alertDialog.dismiss()

            val intent = Intent(this, SellerViewProduct::class.java)
            startActivity(intent)
            finish()
        }


    }

    //update the product information in the Firebase Realtime Database.
    private fun updateProData(
        productId: String,
        proName: String,
        proPrice: String,
        proDescription: String
    ){
        // gets the reference to the Product node in the database
        val dbref = FirebaseDatabase.getInstance().getReference("Product").child(productId)
        //new Product object with the updated values
        val proInfo = Product(proName,proPrice,proDescription,productId, sessionManager.getEmail())
        dbref.setValue(proInfo)
    }


// set the values of the different view elements
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