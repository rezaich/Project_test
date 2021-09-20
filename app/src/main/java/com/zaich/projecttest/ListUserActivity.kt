package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.adapter.UserAdapter
import com.zaich.projecttest.adapter.UserViewHolder
import com.zaich.projecttest.databinding.ActivityListUserBinding
import com.zaich.projecttest.databinding.UserRowBinding
import com.zaich.projecttest.model.Profile

class ListUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListUserBinding
    private lateinit var adapter: UserAdapter
    private var userList = ArrayList<Profile>()
    private lateinit var model : Profile
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = Firebase.firestore
        auth = Firebase.auth
        database = Firebase.database



        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@ListUserActivity)
            rvUser.setHasFixedSize(true)
        }

        getUserList()
    }

    fun getUserList() {
        val currentUser = auth.currentUser
        val reference = database.getReference("users")
        val option = FirebaseRecyclerOptions.Builder<Profile>()
            .setQuery(reference, Profile::class.java)
            .build()
        val adapter = object : FirebaseRecyclerAdapter<Profile, UserViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = UserRowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: Profile) {
                holder.setUser(this@ListUserActivity, model, currentUser!!.uid)

                holder.itemView.setOnClickListener { view ->
                    val userItem = model
                    val intent = Intent(view.context, ChatActivity::class.java)
                    intent.putExtra(ChatActivity.EXTRA_USER,userItem)

                    startActivity(intent)
                }
            }
        }
        adapter.startListening()
        binding.rvUser.adapter = adapter
    }
}
