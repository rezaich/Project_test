package com.zaich.projecttest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.source.smoothstreaming.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.ChatActivity
import com.zaich.projecttest.databinding.ActivityChatBinding
import com.zaich.projecttest.databinding.ItemLeftBinding
import com.zaich.projecttest.databinding.ItemRightBinding
import com.zaich.projecttest.model.Chat
import io.grpc.InternalChannelz.id

class ChatAdapter(private val chatList : ArrayList<Chat> , val context: Context):RecyclerView.Adapter<ChatAdapter.ChatViewHolder1>() {

    private val MESSEGE_TYPE_RIGHT = 1
    private val MESSEGE_TYPE_LEFT = 0
    val firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder1 {
           val binding = ItemRightBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           return ChatViewHolder1(binding)

    }

    override fun onBindViewHolder(holder: ChatViewHolder1, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int = chatList.size

    inner class ChatViewHolder1(
        val binding: ItemRightBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(chat:Chat){
            binding.tvMessage.text = chat.message
        }
    }

/*    inner class ChatViewHolder(view: View):RecyclerView.ViewHolder(view){
    }*/
}