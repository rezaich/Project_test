package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.adapter.UserAdapter
import com.zaich.projecttest.databinding.ActivityListUserBinding
import com.zaich.projecttest.model.Profile

class ListUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListUserBinding
    private lateinit var adapter : UserAdapter
    private var userList = ArrayList<Profile>()
    private lateinit var fireStore : FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = Firebase.firestore
        auth = Firebase.auth

        getUserList()
        adapter = UserAdapter(userList, this@ListUserActivity)
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@ListUserActivity)
            rvUser.adapter = adapter
            rvUser.setHasFixedSize(true)
        }


    }

    fun getUserList() {
        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            val reference = fireStore.collection("users").document(uid)
            val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (dataSnapshot:DataSnapshot in snapshot.children){
                        val user = dataSnapshot.getValue(Profile::class.java)

                        if(user!!.uid.equals(uid)){
                            userList.add(user)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                }
            })

            adapter = UserAdapter(userList, this@ListUserActivity)
            adapter.notifyDataSetChanged()
            }
        }
    }

/*    fun getUserList(){
        var fireBase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (dataSnapshot:DataSnapshot in snapshot.children){
                    val user = dataSnapshot.getValue(Profile::class.java)

                    if(user!!.uid.equals(fireBase.uid)){
                        userList.add(user)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        adapter = UserAdapter(userList, this@ListUserActivity)
        adapter.notifyDataSetChanged()
    }*/
