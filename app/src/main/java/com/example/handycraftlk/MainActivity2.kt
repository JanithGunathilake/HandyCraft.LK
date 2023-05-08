package com.example.handycraftlk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.handycraftlk.adaptors.RecyclerViewAdapter
import com.example.handycraftlk.adaptors.RecyclerViewAdapter

import com.example.handycraftlk.models.RecyclerViewItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity()  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var adapter: RecyclerViewAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val addButton: Button = findViewById(R.id.addButton)

        database = FirebaseDatabase.getInstance().reference

        addButton.setOnClickListener {
            val items = adapter.getItems()
            addItemsToFirebase(items)
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val items = getRecyclerViewItems()
        adapter = RecyclerViewAdapter(items)
        recyclerView.adapter = adapter
    }

    private fun getRecyclerViewItems(): List<RecyclerViewItem> {
        // TODO: Replace with your own logic to fetch the RecyclerView items
        return listOf(
            RecyclerViewItem("1", "Item 1", "Description 1"),
            RecyclerViewItem("2", "Item 2", "Description 2"),
            RecyclerViewItem("3", "Item 3", "Description 3")
        )
    }

    private fun addItemsToFirebase(items: List<RecyclerViewItem>) {
        val itemsRef = database.child("items")

        for (item in items) {
            itemsRef.child(item.id).setValue(item)
                .addOnSuccessListener {
                    // Item added successfully
                }
                .addOnFailureListener { exception ->
                    // Handle any errors
                }
        }
    }
}