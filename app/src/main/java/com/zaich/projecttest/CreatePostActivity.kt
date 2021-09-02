package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityCreatePostBinding
import com.zaich.projecttest.model.Post
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreatePostBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var post: Post
    private var type = "text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database
        post = Post()

        binding.btPost.setOnClickListener {
            val currentUser = auth.currentUser
            currentUser?.let {
                val uid = it.uid

                val today = Calendar.getInstance().time
                val formatter  = SimpleDateFormat("dd-mmmm-yyyy hh:mm:ss")
                val time = formatter.format(today)

                post.text = binding.etPost.text.toString()
                post.type = type
                post.time = time
                post.uid = uid

                val reference = database.getReference("posts")
                val postId = reference.push().key!!
                reference.child(postId).setValue(post).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                }
            }
        }

    }
}