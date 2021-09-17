package com.zaich.projecttest.adapter

import android.app.Activity
import android.net.Uri
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.zaich.projecttest.databinding.PostLayoutBinding
import com.zaich.projecttest.model.Post

class PostViewHolder(val binding: PostLayoutBinding ):RecyclerView.ViewHolder(binding.root) {
    private lateinit var exoPlayer: SimpleExoPlayer

    fun setPost(activity: FragmentActivity, post: Post, userId: String?){

        binding.tvName.text = post.uid
        binding.tvText.text = post.text
        binding.tvTime.text = post.time

        when(post.type){
            "image"->{
                binding.ivPost.visibility = View.VISIBLE

                Glide.with(activity).load(post.postUri).into(binding.ivPost)
            }
            "video"->{
                binding.pvVideo.visibility = View.VISIBLE

                exoPlayer = SimpleExoPlayer.Builder(activity).build()
                val dataSourceFactory = DefaultDataSourceFactory(activity)
                val mediaItem = MediaItem.fromUri(Uri.parse(post.postUri))
                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                binding.pvVideo.player = exoPlayer
            }
            "audio"->{
                binding.pvAudio.visibility =View.VISIBLE

                exoPlayer = SimpleExoPlayer.Builder(activity).build()
                val dataSourceFactory = DefaultDataSourceFactory(activity)
                val mediaItem = MediaItem.fromUri(Uri.parse(post.postUri))
                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                binding.pvAudio.player = exoPlayer
            }
        }

        if (post.uid != userId){
            binding.btPostDelete.visibility = View.GONE
        }
    }
}