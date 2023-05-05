package com.example.handycraftlk.adaptors

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.R
import com.example.handycraftlk.models.Product

class MyAdaptor(private val productList : ArrayList<Product>) : RecyclerView.Adapter<MyAdaptor.MyViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner = clickListner
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_page_items,parent,false)
        return MyViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = productList[position]

        holder.proName.text = currentitem.proName
        holder.proPrice.text = currentitem.proPrice
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(itemView : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val proName : TextView = itemView.findViewById(R.id.tvItemName)
        val proPrice : TextView = itemView.findViewById(R.id.tvPrice)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }


    }


}