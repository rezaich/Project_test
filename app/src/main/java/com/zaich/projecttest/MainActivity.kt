package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.ibToast.setOnClickListener {
            val bottomMenu=BottomMenu()
            bottomMenu.show(supportFragmentManager,"bottomSheet")
        }

        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            val firestore= Firebase.firestore
            val reference = firestore.collection("users").document(uid)

            reference.get().addOnCompleteListener {
                it.result?.let {
                    if (!it.exists()){
                        startActivity(Intent(this,CreateProfileActivity::class.java))
                    }
                }
            }
        }
    }
}