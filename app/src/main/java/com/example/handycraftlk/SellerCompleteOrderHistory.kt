package com.example.handycraftlk

import SessionManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.CompleteOrderAdapter
import com.example.handycraftlk.models.Order
import com.google.firebase.database.*

class SellerCompleteOrderHistory : Fragment() {


    private lateinit var dbref : DatabaseReference
    private lateinit var orderRecycleView : RecyclerView
    private lateinit var orderArrayList : ArrayList<Order>
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_seller_complete_order_history, container, false)


        orderRecycleView = view.findViewById(R.id.rt)
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
                    orderRecycleView.adapter = CompleteOrderAdapter(orderArrayList)                }
            }
            override fun onCancelled(error: DatabaseError) {                // Handle onCancelled event here
            }
        })    }




    private fun fetchPendingOrders() {
        sessionManager = SessionManager(requireContext())
        val userEmail = sessionManager.getSession().get(SessionManager.KEY_EMAIL)
        val dbRef = FirebaseDatabase.getInstance().getReference("Order")
        val query = dbRef.orderByChild("sellerId").equalTo(userEmail)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderArrayList = ArrayList<Order>()
                for (itemSnapshot in snapshot.children) {
                    val order = itemSnapshot.getValue(Order::class.java)
                    val status = order?.status
                    if (status == "Complete") {
                    order?.let { orderArrayList.add(it) }
                }}
                // populate the RecyclerView with the data
                val adapter = CompleteOrderAdapter(orderArrayList)
                orderRecycleView.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }
}