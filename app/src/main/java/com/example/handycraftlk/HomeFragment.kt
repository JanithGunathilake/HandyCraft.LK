package com.example.handycraftlk


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handycraftlk.adaptors.MyAdaptor
import com.example.handycraftlk.models.Product
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var dbref : DatabaseReference
    private lateinit var productRecycleView : RecyclerView
    private lateinit var productArrayList : ArrayList<Product>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater.inflate(R.layout.fragment_home, container, false)

        productRecycleView = view.findViewById(R.id.rvItems)
        productRecycleView.layoutManager = LinearLayoutManager(requireContext())
        productRecycleView.setHasFixedSize(true)

        productArrayList = arrayListOf<Product>()
        getProductData()
        return view




    }


    private fun getProductData() {
        dbref = FirebaseDatabase.getInstance().getReference("Product")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (productSnapshot in snapshot.children){

                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)

                    }
                    val myAdaptor = MyAdaptor(productArrayList)

                    productRecycleView.adapter = myAdaptor

                    myAdaptor.setOnItemClickListner(object : MyAdaptor.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), SingleProductView::class.java)

                            val product = productArrayList[position]

                            //put extras
                            intent.putExtra("sellerEmail", product.sellerEmail)
                            intent.putExtra("productId", product.productId)
                            intent.putExtra("proName", product.proName)
                            intent.putExtra("proPrice", product.proPrice)
                            intent.putExtra("proDescription", product.proDescription)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event here
            }

        })
    }
}
