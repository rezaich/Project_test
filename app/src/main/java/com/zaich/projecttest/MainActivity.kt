package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityMainBinding
import com.zaich.projecttest.model.Post
import com.zaich.projecttest.databinding.PostLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = Firebase.database
        recyclerView = findViewById(R.id.rvList)
        val linearlayout = LinearLayoutManager(this)
        recyclerView.layoutManager = linearlayout

        getRecycler()

        binding.ibToast.setOnClickListener {
            val bottomMenu = BottomMenu()
            bottomMenu.show(supportFragmentManager, "bottomSheet")
        }

/*        binding.btLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }*/

        binding.btPost.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }
    }

    private fun getRecycler() {
        //Go to create profile if profile data not exist
        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            val firestore = Firebase.firestore
            val reference = firestore.collection("users").document(uid)

            reference.get().addOnCompleteListener {
                it.result?.let {
                    if (!it.exists()) {
                        val intent = Intent(this, CreateProfileActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        val reference = database.getReference("posts")
        val options = FirebaseRecyclerOptions.Builder<Post>()
            .setQuery(reference, Post::class.java)
            .build()
        val adapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
/*                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
                return PostViewHolder(view)*/

                val view =
                    PostLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostViewHolder(view)
            }

            override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
                holder.setPost(this@MainActivity, model, currentUser?.uid)

                val postKey = getRef(position).key!!

                holder.binding.btPostDelete.setOnClickListener {
                    val reference = database.getReference("posts").child(postKey)
                    reference.removeValue()
                }
            }
        }
        adapter.startListening()
        recyclerView.adapter = adapter
    }

}
/*    override fun onStart() {
        super.onStart()
        val reference = database.getReference("posts")
        val options = FirebaseRecyclerOptions.Builder<Post>()
            .setQuery(reference,Post::class.java)
            .build()
        val adapter = object  :  FirebaseRecyclerAdapter<Post,PostViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
                return PostViewHolder(view)

*//*                val view = PostLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return PostViewHolder(view)*//*
            }

            override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
                holder.setPost(this@MainActivity, model, auth.currentUser!!.uid)

                val postKey = getRef(position).key!!

                holder.btDelete.setOnClickListener {
                    val reference = database.getReference("posts").child(postKey)
                    reference.removeValue()
                }
            }
        }
        adapter.startListening()
        binding.rvList.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}*/

/*class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var postButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.mainLogoutButton)
        postButton = findViewById(R.id.mainPostButton)
        recyclerView = findViewById(R.id.mainRecyclerView)
        auth = Firebase.auth
        database = Firebase.database

        button.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        postButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
    }

*//*    override fun onStart() {
        super.onStart()

        val reference = database.getReference("posts")
        val options = FirebaseRecyclerOptions.Builder<Post>()
            .setQuery(reference, Post::class.java)
            .build()

        val adapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.post_layout, parent, false)

                return PostViewHolder(view)
            }

            override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
                holder.setPost(this@MainActivity, model, auth.currentUser!!.uid)

                val postKey = getRef(position).key!!

                holder.deleteButton.setOnClickListener {
                    val reference = database.getReference("posts").child(postKey)
                    reference.removeValue()
                }
            }

        }

        adapter.startListening()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }*//*
}*/
