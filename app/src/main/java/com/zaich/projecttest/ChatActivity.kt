package com.zaich.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityChatBinding
import com.zaich.projecttest.model.Chat
import com.zaich.projecttest.model.Profile

class ChatActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "EXTRA USER"
    }

    private lateinit var fireStore : FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var chat: Chat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = Firebase.firestore
        auth = Firebase.auth
        database = Firebase.database
        chat = Chat()
        val currentUser = auth.currentUser

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

        val getModel = intent.getParcelableExtra<Profile>(EXTRA_USER) as Profile

        binding.tvUserName.text = getModel.name

        binding.btnSendMessage.setOnClickListener {
            var messege: String = binding.etMessage.text.toString()

            if (messege.isEmpty()){
                Toast.makeText(applicationContext, "messege empty", Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(currentUser!!.uid , getModel.uid!! ,messege)
            }
        }
    }

    private fun sendMessage(senderId : String,receiverId : String,messege:String){
        var reference = database.getReference("messeges")
        val messegeId = reference.push().key!!
        reference.child(messegeId).setValue(chat).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "Messeges sended", Toast.LENGTH_SHORT).show()
            }
        }
    }
}