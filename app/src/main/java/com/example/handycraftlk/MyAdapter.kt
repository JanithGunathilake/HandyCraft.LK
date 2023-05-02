package com.example.handycraftlk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private  val itemList:ArrayList<Order> ): RecyclerView.Adapter <MyAdapter.MyViewHolder>(){

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.checkout_r,
          parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem= itemList[position]
        holder.itemName.text=currentItem.itemName
        holder.quantity.text=currentItem.quantity
        holder.totalAmount.text=currentItem.totalAmount
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val itemName:TextView=itemView.findViewById(R.id.pname)
        val quantity:TextView=itemView.findViewById(R.id.pquantity)
        val totalAmount:TextView=itemView.findViewById(R.id.totalPrice)
    }

}