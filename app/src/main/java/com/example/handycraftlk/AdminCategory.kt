package com.example.handycraftlk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.MyAdaptorD
import com.example.handycraftlk.databinding.ActivityAdminCategoryBinding
import com.example.handycraftlk.models.Category
import com.google.firebase.database.*

class AdminCategory : AppCompatActivity() {

    private lateinit var binding: ActivityAdminCategoryBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var categoryRecyclerView : RecyclerView
    private lateinit var categoryArrayList : ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryRecyclerView = findViewById(R.id.rvCategory)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        categoryRecyclerView.setHasFixedSize(true)

        categoryArrayList = arrayListOf<Category>()
        getCategoryData()



        val btnAddCategory = findViewById<Button>(R.id.btnNewCat)
        btnAddCategory.setOnClickListener { view ->
            btnAddCategory(view)
        }

        val btnBackImage = findViewById<ImageView>(R.id.btnBackC)
        btnBackImage.setOnClickListener{ view ->
            btnBack(view)
        }


    }

    private fun getCategoryData(){

        dbref = FirebaseDatabase.getInstance().getReference("Category")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (categorySnapshot in snapshot.children){


                        val category = categorySnapshot.getValue(Category::class.java)
                        categoryArrayList.add(category!!)
                    }

                    val myAdaptorD = MyAdaptorD(categoryArrayList)
                    categoryRecyclerView.adapter = myAdaptorD
                    myAdaptorD.setOnItemClickListner(object : MyAdaptorD.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AdminCategory, CategoryUpdateView::class.java)
                            intent.putExtra("categoryId" ,categoryArrayList[position].categoryId)
                            intent.putExtra("categoryName",categoryArrayList[position].categoryName)
                            startActivity(intent)




                        }

                    })





                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }



    private fun btnAddCategory(view: View) {
        val intent = Intent(this, AddCategory::class.java)
        startActivity(intent)
        finish()
    }

    private fun btnBack(view: View) {
        val intent = Intent(this, AdminTool::class.java)
        startActivity(intent)
        finish()
    }
}