package com.example.handycraftlk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.MyAdapter
import com.example.handycraftlk.models.Order
import com.google.firebase.database.*


class CustomerPendingOrderHistory : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var orderRecycleView : RecyclerView
    private lateinit var orderArrayList : ArrayList<Order>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customer_pending_order_history, container, false)


        orderRecycleView = view.findViewById(R.id.deliveryPending)
        orderRecycleView.layoutManager = LinearLayoutManager(requireContext())
        orderRecycleView.setHasFixedSize(true)
        orderArrayList = arrayListOf<Order>()

        fetchPendingOrders()

        return view
    }



    private fun fetchPendingOrders() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Order")
        val query = dbRef.orderByChild("status").equalTo("Pending")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderArrayList = ArrayList<Order>()
                for (itemSnapshot in snapshot.children) {
                    val order = itemSnapshot.getValue(Order::class.java)
                    order?.let { orderArrayList.add(it) }
                }
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