package com.zaich.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.adapter.ChatReceivedViewHolder
import com.zaich.projecttest.databinding.ActivityChatBinding
import com.zaich.projecttest.databinding.ItemLeftBinding
import com.zaich.projecttest.model.Chat
import com.zaich.projecttest.model.Profile
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "EXTRA USER"
    }

    private lateinit var fireStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var chat: Chat
    private var type: String? = "text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = Firebase.firestore
        auth = Firebase.auth
        database = Firebase.database
        chat = Chat()


        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.apply {
            chatRecyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
            chatRecyclerView.setHasFixedSize(true)
        }

        val getModel = intent.getParcelableExtra<Profile>(EXTRA_USER) as Profile

        binding.tvUserName.text = getModel.name

        binding.btnSendMessage.setOnClickListener {
            val currentUser = auth.currentUser
            currentUser?.let {
                val uid = it.uid
                val today = Calendar.getInstance().time
                val formatter = SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss")
                val time = formatter.format(today)

                var messege: String = binding.etMessage.text.toString()
                chat.time = time
                chat.type = type
                chat.senderUid = uid
                chat.receiverUid = getModel.uid
                chat.message = messege

                uid.let {
                    var reference = database.getReference("messages")
                    val messegeId = reference.push().key!!

                    reference.child(uid).child(getModel.uid!!).child(messegeId).setValue(chat)
                        .addOnCompleteListener { sendMessage ->
                            if (sendMessage.isSuccessful) {
                                Toast.makeText(this, "Messages Sending", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    reference.child(getModel.uid!!).child(uid).child(messegeId).setValue(chat)
                        .addOnCompleteListener { receivedMessage ->
                            if (receivedMessage.isSuccessful) {
                                Toast.makeText(this, "Messages Sending", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }

            }
        }
        getChat()
    }

    private fun getChat() {
        val currentUser = auth.currentUser
        val uid = currentUser?.uid
        val getModel = intent.getParcelableExtra<Profile>(EXTRA_USER) as Profile
        val reference1 = database.getReference("messages/$uid/${getModel.uid}")
        val option1 = FirebaseRecyclerOptions.Builder<Chat>()
            .setQuery(reference1, Chat::class.java)
            .build()

        val adapter =
            object : FirebaseRecyclerAdapter<Chat, ChatReceivedViewHolder>(option1) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ChatReceivedViewHolder {
                    val view = ItemLeftBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    return ChatReceivedViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: ChatReceivedViewHolder,
                    position: Int,
                    model: Chat
                ) {
                    holder.setUserReceiver(
                        this@ChatActivity,
                        model,
                        uid!!
                    )
                }
            }
        adapter.startListening()
        binding.chatRecyclerView.adapter = adapter
    }
}