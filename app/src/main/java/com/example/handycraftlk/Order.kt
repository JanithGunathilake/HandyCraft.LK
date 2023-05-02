package com.example.handycraftlk

data class Order(val address:String?=null,
                 val visaCard:String?= null,
                 val cvv:String?= null,
                 val quantity:String?= null,
                 val itemName: String?=null,
                 val totalAmount:String?=null,
                 val cusId:String?=null,
                 val orderId:String?=null,
                 val dt:String?=null,
                 )
