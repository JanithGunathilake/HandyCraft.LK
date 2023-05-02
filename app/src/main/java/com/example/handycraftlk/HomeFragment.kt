package com.example.handycraftlk


import SessionManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class HomeFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sessionManager = SessionManager(requireContext())
        if (!sessionManager.isLoggedIn()) {
            val intent = Intent(activity, LoginPage::class.java)
            startActivity(intent)
            activity?.finish()
        }


            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_home, container, false)

    }}
