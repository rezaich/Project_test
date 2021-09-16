package com.zaich.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.model.Profile
import com.zaich.projecttest.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "EXTRA USER"
    }

    private var firebaseUser : FirebaseUser? = null
    private var reference : DatabaseReference? = null
    private lateinit var binding:ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectUser = intent.getParcelableExtra<Profile>(EXTRA_USER) as Profile

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(selectUser.uid!!)

        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Profile::class.java)
                binding.tvUserName.text = user?.name
                if (user?.url == ""){
                    binding.imgProfile.setImageResource(R.mipmap.ic_launcher_round)
                }else{
                    Glide.with(this@ChatActivity).load(user?.url).into(binding.imgProfile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}