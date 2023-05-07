package com.example.handycraftlk

import SessionManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.COrderAdapter
import com.example.handycraftlk.adaptors.MyAdapter

import com.example.handycraftlk.models.Order
import com.google.firebase.database.*
import java.io.Console


class CustomerProcessOrderHistory : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var orderRecycleView : RecyclerView
    private lateinit var orderArrayList : ArrayList<Order>
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customer_process_order_history, container, false)


        orderRecycleView = view.findViewById(R.id.prpending)
        orderRecycleView.layoutManager = LinearLayoutManager(requireContext())
        orderRecycleView.setHasFixedSize(true)
        orderArrayList = arrayListOf<Order>()
        //getPendingOrderData()
        //getOrderData()
        fetchPendingOrders()

        return view
    }
    private fun getOrderData() {
        dbref = FirebaseDatabase.getInstance().getReference("Order")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (itemSnapShot in snapshot.children){
                        val order = itemSnapShot.getValue(Order::class.java)
                        orderArrayList.add(order!!)
                    }
                    orderRecycleView.adapter = COrderAdapter(orderArrayList)                }
            }
            override fun onCancelled(error: DatabaseError) {                // Handle onCancelled event here
            }
        })    }


    private fun fetchPendingOrders() {
        sessionManager = SessionManager(requireContext())
        val userEmail = sessionManager.getSession().get(SessionManager.KEY_EMAIL)
        val dbRef = FirebaseDatabase.getInstance().getReference("Order")
        val query = dbRef.orderByChild("status").equalTo("Processing")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderArrayList = ArrayList<Order>()
                for (itemSnapshot in snapshot.children) {
                    val order = itemSnapshot.getValue(Order::class.java)
                    val email = order?.email
                    if (email == userEmail) {
                        order?.let { orderArrayList.add(it) }
                    }}
                // populate the RecyclerView with the data
                val adapter = MyAdapter(orderArrayList)
                orderRecycleView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }

}