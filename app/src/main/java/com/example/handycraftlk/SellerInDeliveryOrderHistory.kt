package com.example.handycraftlk

import SessionManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.SIndeliveryAdapter

import com.example.handycraftlk.models.Order
import com.google.firebase.database.*

class SellerInDeliveryOrderHistory : Fragment() {

    private lateinit var dbref : DatabaseReference
    private lateinit var orderRecycleView : RecyclerView
    private lateinit var orderArrayList : ArrayList<Order>
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_seller_in_delivery_order_history, container, false)



        orderRecycleView = view.findViewById(R.id.deliveryhistory)
        orderRecycleView.layoutManager = LinearLayoutManager(context)
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
                    orderRecycleView.adapter = SIndeliveryAdapter(orderArrayList)                }
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
                    if (status == "In Delivery") {
                    order?.let { orderArrayList.add(it) }
                }}
                // populate the RecyclerView with the data
                val adapter = SIndeliveryAdapter(orderArrayList)
                orderRecycleView.adapter = adapter

                adapter.setOnItemClickListner(object : SIndeliveryAdapter.onItemClickListner{
                    override fun onItemClick(position: Int) {
                        val intent= Intent(this@SellerInDeliveryOrderHistory.context,popdelivery::class.java)
                        intent.putExtra("orderId",orderArrayList[position].orderId)
                        intent.putExtra("quantityView",orderArrayList[position].quantity)
                        intent.putExtra("addressView",orderArrayList[position].address)
                        intent.putExtra("productView",orderArrayList[position].itemName)

                        startActivity(intent)
                    }

                })
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }
}