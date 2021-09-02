package com.zaich.projecttest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityMainBinding
import com.zaich.projecttest.model.Post

/*
class MainViewModel: ViewModel (){

        private lateinit var binding: ActivityMainBinding
       fun setPost(post:String){
           val reference = Firebase.database.getReference("posts")
           val options = FirebaseRecyclerOptions.Builder<Post>()
               .setQuery(reference,Post::class.java)
               .build()
           val adapter = object  :  FirebaseRecyclerAdapter<Post,PostViewHolder>(options){
               override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                   val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
                   return PostViewHolder(view)

//                val view = PostLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//                return PostViewHolder(view)
               }

               override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
                   holder.setPost(this@MainViewModel, model, Firebase.auth.currentUser!!.uid)

                   val postKey = getRef(position).key!!

                   holder.btDelete.setOnClickListener {
                       val reference = Firebase.database.getReference("posts").child(postKey)
                       reference.removeValue()
                   }
               }
           }
           adapter.startListening()
           binding.rvList.setHasFixedSize(true)
           binding.rvList.layoutManager = LinearLayoutManager(this)
           binding.rvList.adapter = adapter
       }
}*/
