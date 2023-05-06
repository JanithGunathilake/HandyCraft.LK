package com.example.handycraftlk.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.R
import com.example.handycraftlk.models.Category

    class MyAdaptorD(private val categoryList : ArrayList<Category>) : RecyclerView.Adapter<MyAdaptorD.MyViewHolder>() {

        private lateinit var mListner : onItemClickListner

        interface onItemClickListner{
            fun onItemClick(position: Int)
        }

        fun setOnItemClickListner(clickListner: onItemClickListner){
            mListner = clickListner
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_items,parent,false)
        return MyViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = categoryList[position]

        holder.categoryName.text = currentitem.categoryName

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class MyViewHolder(itemView : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val categoryName : TextView = itemView.findViewById(R.id.tvCategoryName)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }

    }
}