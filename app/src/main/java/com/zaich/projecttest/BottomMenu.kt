package com.zaich.projecttest

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.BottomMenuBinding

class BottomMenu :BottomSheetDialogFragment(){
    private lateinit var binding: BottomMenuBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomMenuBinding.inflate(inflater,container,false)
        val view = binding.root
        auth = Firebase.auth

        binding.profileCardView.setOnClickListener {
            startActivity(Intent(requireActivity(),ProfileActivity::class.java))
        }

        binding.ListUser.setOnClickListener {
            startActivity(Intent(requireActivity(),ListUserActivity::class.java))
        }

        binding.logoutProfileView.setOnClickListener {
            activity?.let {
                val intent = Intent(it,LoginActivity::class.java)
                val builder = AlertDialog.Builder(requireActivity()).apply {
                    setTitle("logout")
                    setMessage("Are You Sure to Logout?")
                    setPositiveButton("Logout"){_,_ ->
                        auth.signOut()
                        startActivity(intent)
                    }
                }
                builder.create()
                builder.show()
            }
        }
        return view
    }
}
