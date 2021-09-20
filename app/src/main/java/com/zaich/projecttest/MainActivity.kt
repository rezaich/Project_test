package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.zaich.projecttest.adapter.PostViewHolder
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
        currentUser!!.getIdToken(false)
            .addOnCompleteListener {
                val token = it.result!!.token
                //request ke endpoinr Laravel login firebase
                //parameter yang berisi token(firebase.token)
            }

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
