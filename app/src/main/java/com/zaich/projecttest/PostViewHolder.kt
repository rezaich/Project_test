package com.zaich.projecttest

import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.zaich.projecttest.databinding.PostLayoutBinding
import com.zaich.projecttest.model.Post

class PostViewHolder(val binding: PostLayoutBinding ):RecyclerView.ViewHolder(binding.root) {


    fun setPost(activity: FragmentActivity, post: Post, userId: String?){

        binding.tvName.text = post.uid
        binding.tvText.text = post.text
        binding.tvTime.text = post.time

        if (post.uid != userId){
            binding.btPostDelete.visibility = View.GONE
        }
    }
}

/*class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var nameTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var textView: TextView
    lateinit var btDelete: Button

    fun setPost(activity: FragmentActivity, post: Post, userId: String?) {
        nameTextView = itemView.findViewById(R.id.tvName)
        timeTextView = itemView.findViewById(R.id.tvTime)
        textView = itemView.findViewById(R.id.tvText)
        btDelete = itemView.findViewById(R.id.btPostDelete)

        nameTextView.text = post.uid
        timeTextView.text = post.time
        textView.text = post.text

        if (post.uid != userId) {
            btDelete.visibility = View.GONE
        }
    }
}*/
