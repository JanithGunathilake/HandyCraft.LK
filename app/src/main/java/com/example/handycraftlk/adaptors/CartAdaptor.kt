package com.example.handycraftlk.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.R
import com.example.handycraftlk.models.Cart

class CartAdaptor(private val cartList : ArrayList<Cart>) : RecyclerView.Adapter<CartAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_view,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = cartList[position]

        holder.proName.text = currentitem.proName
        holder.proPrice.text = currentitem.proPrice
    }

    override fun getItemCount(): Int {
       return cartList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val proName : TextView = itemView.findViewById(R.id.tvProductName)
        val proPrice : TextView = itemView.findViewById(R.id.tvProductPrice)

    }

}