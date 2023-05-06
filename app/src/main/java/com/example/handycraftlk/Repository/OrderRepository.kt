package com.example.handycraftlk.Repository

import androidx.lifecycle.MutableLiveData
import com.example.handycraftlk.models.Order
import com.google.firebase.database.*

class OrderRepository {

   private val databaseReference:DatabaseReference=FirebaseDatabase.getInstance().getReference("Order")
    @Volatile private var INSTANCE:OrderRepository?= null
    fun getInstances():OrderRepository{
        return INSTANCE ?: synchronized(this){
            val instance=OrderRepository()
            INSTANCE=instance
            instance
        }
    }
    fun loadOrders(orderList:MutableLiveData<List<Order>>){
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try{
                    val _orderList: List<Order> = snapshot.children.map{dataSnapshot ->
                        dataSnapshot.getValue(Order::class.java)!!
                    }

                   orderList.postValue(_orderList)
                }catch (e :Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}