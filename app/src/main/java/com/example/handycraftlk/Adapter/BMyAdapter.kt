package com.example.handycraftlk.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.R
import com.example.handycraftlk.models.Order


class BMyAdapter : RecyclerView.Adapter<BMyAdapter.MyViewHolder>(){


    private val OrderList= ArrayList<Order>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(
            R.layout.order_item,
            parent,false
        )
        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: BMyAdapter.MyViewHolder, position: Int) {

        val cutrrentItem =OrderList[position]

        holder.orderiD.text=cutrrentItem.orderId
        holder.date.text=cutrrentItem.dt
        holder.itemName.text=cutrrentItem.itemName
        holder.quantity.text= cutrrentItem.quantity





    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

fun updateOrderList(orderList :List<Order>){
    this.OrderList.clear()
    this.OrderList.addAll(orderList)
    notifyDataSetChanged()
}
    class MyViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        val itemName: TextView=itemView.findViewById(R.id.pname)
        val quantity: TextView=itemView.findViewById(R.id.pquantity)
        val date: TextView=itemView.findViewById(R.id.dateTime)
        val orderiD: TextView =itemView.findViewById(R.id.orderId)

    }


}