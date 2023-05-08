package com.example.handycraftlk

import SessionManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.CartAdaptor
import com.example.handycraftlk.models.Cart
import com.google.firebase.database.*

class CartFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var dbref : DatabaseReference
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartArrayList : ArrayList<Cart>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        cartRecyclerView = view.findViewById(R.id.rvCartItem)
        cartRecyclerView.setHasFixedSize(true)
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        cartArrayList = arrayListOf<Cart>()

        sessionManager = SessionManager(requireContext())
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(activity, LoginPage::class.java)
            startActivity(intent)
            activity?.finish()
        }

        getCartData()

        return view

    }

    private fun getCartData() {
        val userEmail = sessionManager.getEmail()
        dbref = FirebaseDatabase.getInstance().getReference("Cart")

        dbref.orderByChild("buyerEmail").equalTo(userEmail)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var totalPrice = 0.0
                        for (cartSnapshot in snapshot.children) {
                            val cart = cartSnapshot.getValue(Cart::class.java)
                            cartArrayList.add(cart!!)
                            totalPrice += cart.proPrice?.toDoubleOrNull() ?: 0.0
                        }
                        cartRecyclerView.adapter = CartAdaptor(cartArrayList)
                        // Show the total price on the screen
                        val totalPriceTextView = view?.findViewById<TextView>(R.id.totalPriceTextView)
                        totalPriceTextView?.text = "Total Price: $totalPrice"
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle error here
                }
            })
    }


}
