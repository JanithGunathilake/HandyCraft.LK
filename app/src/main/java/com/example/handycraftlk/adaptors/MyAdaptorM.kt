package com.example.handycraftlk.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.R
import com.example.handycraftlk.models.Product

class MyAdaptorM(private val productList : ArrayList<Product>) : RecyclerView.Adapter<MyAdaptorM.MyViewHolder>() {

    private lateinit var mListner : onItemClickListner

            interface onItemClickListner{
                fun onItemClick(position: Int)//provide a callback to notify that an item in the list has been clicked
            }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner = clickListner// allows the adapter to call the onItemClick function of the interface when an item is clicked
    }






   //called by recycle view when it needs a new viewholder to display an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_view_item,parent,false)
        return MyViewHolder(itemView,mListner)
    }

    //called by recycle view to bind data to viewHolder and sets value to the textviews
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = productList[position]

        holder.proName.text = currentitem.proName
        holder.proPrice.text = currentitem.proPrice
    }

    //total number of items in the list
    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(itemView : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val proName : TextView = itemView.findViewById(R.id.tvNameProduct)//initialized in the constructor
        val proPrice : TextView = itemView.findViewById(R.id.tvProPrice)

        //executed when an instance of the class is created
        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)//returns the position of the ViewHolder in the adapter.
            }
        }


    }
}