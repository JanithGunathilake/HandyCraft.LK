package com.example.handycraftlk

import SessionManager
import com.example.handycraftlk.databinding.FragmentAccountBinding




import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.database.*


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var database: DatabaseReference
    private lateinit var sessionManager: SessionManager
    private lateinit var myButton: Button
    private lateinit var AccInfo: Button
    private lateinit var AboutUs : Button



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        binding = FragmentAccountBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        val userEmail = sessionManager.getEmail()

        database = FirebaseDatabase.getInstance().reference

        val userRef = database.child("Users")
        val query = userRef.orderByChild("email").equalTo(userEmail)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.value as HashMap<String, String>
                        val name = user["name"]
                        val email = user["email"]


                        binding.tvName.text = name
                        binding.tvEmailAcc.text = email
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read user details", error.toException())
            }
        })


        AccInfo = binding.root.findViewById(R.id.btnAccInfo)
        AccInfo.setOnClickListener {

            val intent = Intent(activity, AccInfomation::class.java)
            startActivity(intent)
            activity?.finish()

        }

        AboutUs = binding.root.findViewById(R.id.btnAboutUs)
        AboutUs.setOnClickListener {

            val intent = Intent(activity, AboutUsPage::class.java)
            startActivity(intent)
            activity?.finish()

        }


        myButton = binding.root.findViewById(R.id.btnLogout)
        myButton.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(activity, LoginPage::class.java)
            startActivity(intent)
            activity?.finish()
            Toast.makeText(activity, "Log Out Successful!", Toast.LENGTH_SHORT).show()

        }



        sessionManager = SessionManager(requireContext())
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(activity, LoginPage::class.java)
            startActivity(intent)
            activity?.finish()
        }





        // Inflate the layout for this fragment
        return binding.root




    }


}
