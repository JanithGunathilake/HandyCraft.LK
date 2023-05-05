package com.example.handycraftlk.models

data class Order(val address:String?=null,
                 val visaCard:String?= null,
                 val cvv:String?= null,
                 val quantity:String?= null,
                 val itemName: String?=null,
                 val totalAmount:String?=null,
                 val email:String?=null,
                 val orderId:String?=null,
                 val dt:String?=null,
                 val status:String?=null,
                 val sellerId:String?=null,

                 )
