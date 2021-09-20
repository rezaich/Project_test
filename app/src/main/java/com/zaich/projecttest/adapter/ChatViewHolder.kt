package com.zaich.projecttest.adapter

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.zaich.projecttest.databinding.ItemLeftBinding
import com.zaich.projecttest.model.Chat



class ChatReceivedViewHolder(val binding: ItemLeftBinding):RecyclerView.ViewHolder(binding.root) {


    fun setUserReceiver(activity:FragmentActivity,list: Chat,userId: String){
        binding.tvReceivedMessage.text = list.message
        binding.tvSenderMessage.text = list.message

        when(userId){
            list.senderUid -> {
                binding.layoutUserReceived.visibility = View.GONE
                binding.layoutUserSender.visibility = View.VISIBLE
            }
            list.receiverUid -> {
                binding.layoutUserReceived.visibility = View.VISIBLE
                binding.layoutUserSender.visibility = View.GONE
            }
        }
/*        if (userId == list.senderUid) {
            binding.layoutUserReceived.visibility = View.GONE
            binding.layoutUserSender.visibility = View.VISIBLE
        }
        else if (userId == list.receiverUid){
            binding.layoutUserReceived.visibility = View.VISIBLE
            binding.layoutUserSender.visibility = View.GONE
        }*/
    }
}
