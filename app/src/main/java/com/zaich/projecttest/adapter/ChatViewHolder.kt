package com.zaich.projecttest.adapter

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaich.projecttest.databinding.ItemLeftBinding
import com.zaich.projecttest.databinding.ItemRightBinding
import com.zaich.projecttest.model.Chat
import com.zaich.projecttest.model.Profile

class ChatSendViewHolder(val binding: ItemRightBinding):RecyclerView.ViewHolder(binding.root) {

    fun setUserSender(activity:FragmentActivity,list: Chat,userId: String){
        binding.tvMessage.text = list.message
    }
}
class ChatReceivedViewHolder(val binding: ItemLeftBinding):RecyclerView.ViewHolder(binding.root) {

    fun setUserReceiver(activity:FragmentActivity,list: Chat,userId: String){
        binding.tvMessage.text = list.message
    }
}
