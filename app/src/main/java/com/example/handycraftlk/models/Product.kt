package com.example.handycraftlk.models

//creating a data class
data class Product(
    var proName: String? = null,//mutable properties
    var proPrice: String? = null,
    var proDescription: String? = null,
    var productId : String? = null,
    var sellerEmail : String? = null
//    var category: String? = null,
//    var proImg: String? = null
)
