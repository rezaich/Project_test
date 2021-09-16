package com.zaich.projecttest.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaich.projecttest.ChatActivity
import com.zaich.projecttest.model.Profile
import com.zaich.projecttest.databinding.UserRowBinding


class UserAdapter(private val list: ArrayList<Profile>, val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder>()  {

    inner class UserAdapterViewHolder(private val binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
                binding.tvName.text = profile.name
                Glide.with(itemView).load(profile.url).into(binding.imgPhoto)
                binding.tvEmail.text = profile.uid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapterViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapterViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            val username = list[position]

            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.EXTRA_USER, username)

            context.startActivities(arrayOf(intent))
        }
    }

    override fun getItemCount(): Int = list.size

}