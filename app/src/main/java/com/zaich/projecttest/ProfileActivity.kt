package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var fireStore : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = Firebase.firestore
        auth = Firebase.auth

        binding.ibBackProfile.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            val reference = fireStore.collection("users").document(uid)

            reference.get().addOnCompleteListener {
                it.result?.let {
                    if(it.exists()){
                        val name = it.getString("name")
                        val bio = it.getString("bio")
                        val email = it.getString("email")
                        val url = it.getString("url")

                        Glide.with(this).load(url).into(binding.ivProfile)
                        binding.tvNameProfile.text = name
                        binding.profileBioEditText.text = bio
                        binding.profileEmailEditText.text = email
                    }else{
                        startActivity(Intent(this,CreateProfileActivity::class.java))
                    }
                }
            }
        }
    }
}