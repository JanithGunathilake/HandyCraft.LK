package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.handycraftlk.models.Category
import com.google.firebase.database.FirebaseDatabase

class CategoryUpdateView : AppCompatActivity() {

    private lateinit var tvCatName : TextView
    private lateinit var btnCatUp : Button
    private lateinit var btnCatDel : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_update_view)

        tvCatName = findViewById(R.id.tvCatName) // Initialize the TextView
        btnCatUp = findViewById(R.id.btnCatUp) // Initialize the Button
        btnCatDel = findViewById(R.id.btnCatDel)


        setValuesToView()

        btnCatUp.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("categoryId").toString(),
                intent.getStringExtra("categoryName").toString()

            )



        }

        btnCatDel.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("categoryId").toString()
            )
        }

    }

    private fun deleteRecord(
        categoryId: String
    ){
        val dbref = FirebaseDatabase.getInstance().getReference("Category").child(categoryId)
        val mTask = dbref.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Category Deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AdminCategory::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValuesToView() {
        intent.getStringExtra("categoryId")
        tvCatName.text = intent.getStringExtra("categoryName")
    }

    private fun openUpdateDialog(
        categoryId :String,
        categoryName : String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.category_update_dialog, null)

        mDialog.setView(mDialogView)

        val edtCategoryNam = mDialogView.findViewById<EditText>(R.id.edtCategoryNam)
        val btnUpCat = mDialogView.findViewById<Button>(R.id.btnUpCat)

        edtCategoryNam.setText(intent.getStringExtra("categoryName").toString())

        mDialog.setTitle("Updating $categoryName Record ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpCat.setOnClickListener{
            updateCategory(
                categoryId,
                edtCategoryNam.text.toString()
            )

            Toast.makeText(applicationContext, "Category Data Updated", Toast.LENGTH_SHORT).show()

            tvCatName.text = edtCategoryNam.text.toString()

            alertDialog.dismiss()

            val intent = Intent(this, AdminCategory::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun updateCategory(
        categoryId: String,
        categoryName: String
    ){
        val dbref = FirebaseDatabase.getInstance().getReference("Category").child(categoryId)
        val categoryInfo = Category(categoryId,categoryName)
        dbref.setValue(categoryInfo)
    }

}
