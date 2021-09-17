package com.zaich.projecttest.adapter

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaich.projecttest.databinding.UserRowBinding
import com.zaich.projecttest.model.Profile

class UserViewHolder(val binding:UserRowBinding):RecyclerView.ViewHolder(binding.root) {

    fun setUser(activity:FragmentActivity,list: Profile,userId: String){
        binding.tvName.text = list.name
        binding.tvEmail.text = list.uid
        Glide.with(itemView).load(list.url).into(binding.imgPhoto)
    }
}