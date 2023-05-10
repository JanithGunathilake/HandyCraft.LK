package com.example.handycraftlk.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.handycraftlk.Repository.OrderRepository

class OrderViewModel:ViewModel() {
    private val repository : OrderRepository
    private val _allOrders=MutableLiveData<List<Order>>()
    val allOrders:LiveData<List<Order>> = _allOrders

    init {
        repository = OrderRepository().getInstances()
        repository.loadOrders(_allOrders)
    }

}