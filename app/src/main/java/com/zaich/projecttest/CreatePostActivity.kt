package com.zaich.projecttest

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.zaich.projecttest.databinding.ActivityCreatePostBinding
import com.zaich.projecttest.model.Post
import com.zaich.projecttest.model.Profile
import java.text.SimpleDateFormat
import java.util.*


class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var post: Post
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private var selectedUri: Uri? = null
    private var type: String? = "text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        post = Post()

        database = Firebase.database
        storage = Firebase.storage
        auth = Firebase.auth


        //Get audio video image media from device
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result?.resultCode == Activity.RESULT_OK) {
                        result.data?.let {
                            selectedUri = it.data
                            val contentResolver: ContentResolver = getContentResolver()
                            val fileType = contentResolver.getType(selectedUri!!)

                            if (fileType!!.contains("image")) {
                                binding.pvCreatePost.visibility = View.GONE
                                binding.ivCreatePost.visibility = View.VISIBLE
                                binding.cvCreatePostDelete.visibility = View.VISIBLE

                                Glide.with(this).load(selectedUri!!).into(binding.ivCreatePost)
                                type = "image"
                            } else if (fileType!!.contains("video")) {
                                binding.pvCreatePost.visibility = View.VISIBLE
                                binding.ivCreatePost.visibility = View.GONE
                                binding.cvCreatePostDelete.visibility = View.VISIBLE

                                val exoPlayer = SimpleExoPlayer.Builder(this).build()
                                val dataSourceFactory = DefaultDataSourceFactory(this)
                                val mediaItem = MediaItem.fromUri(selectedUri!!)
                                val mediaSource = ProgressiveMediaSource
                                    .Factory(dataSourceFactory)
                                    .createMediaSource(mediaItem)

                                exoPlayer.setMediaSource(mediaSource)
                                exoPlayer.prepare()
                                exoPlayer.playWhenReady = true
                                binding.pvCreatePost.player = exoPlayer
                                type = "video"
                            } else if (fileType!!.contains("audio")) {
                                with(binding) {
                                    pvCreatePost.visibility = View.VISIBLE
                                    ivCreatePost.visibility = View.GONE
                                    cvCreatePostDelete.visibility = View.VISIBLE
                                }

                                val exoPlayer = SimpleExoPlayer.Builder(this).build()
                                val dataSourceFactory = DefaultDataSourceFactory(this)
                                val mediaItem = MediaItem.fromUri(selectedUri!!)
                                val mediaSource = ProgressiveMediaSource
                                    .Factory(dataSourceFactory)
                                    .createMediaSource(mediaItem)

                                exoPlayer.setMediaSource(mediaSource)
                                exoPlayer.prepare()
                                exoPlayer.playWhenReady = true
                                binding.pvCreatePost.player = exoPlayer
                                type = "audio"
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btAttachPost.setOnClickListener {
            val intent = Intent()
            intent.setType("image/* video/* audio/*")
            intent.putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/*", "video/*", "audio/*")
            )
            intent.setAction(Intent.ACTION_GET_CONTENT)
            resultLauncher.launch(intent)
        }

        binding.cvCreatePostDelete.setOnClickListener {
            selectedUri = null
            type = "type"

            with(binding) {
                ivCreatePost.visibility = View.GONE
                pvCreatePost.visibility = View.GONE
            }

            it.visibility = View.GONE
        }

        binding.btCreatePost.setOnClickListener {
            val currentUser = auth.currentUser
            currentUser?.let {
                val uid = it.uid

                val today = Calendar.getInstance().time

                val formatter = SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss")
                val time = formatter.format(today)

                selectedUri?.let {
                    val contentResolver: ContentResolver = getContentResolver()
                    val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
                    val fileExtension =
                        mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(it))

                    val storageReferenece = storage.getReference("posts")
                        .child(System.currentTimeMillis().toString() + ".${fileExtension}")
                    val uploadTask = storageReferenece.putFile(it)
                    uploadTask.continueWithTask {
                        if (!it.isSuccessful) {
                            throw it.exception!!.cause!!
                        }

                        storageReferenece.downloadUrl
                    }.addOnCompleteListener {
                        if (it.isSuccessful) {
                            it.result?.let {
                                val downloadedUri = it

                                post.text = binding.etCreatePost.text.toString()
                                post.type = type
                                post.time = time
                                post.uid = uid
                                post.postUri = downloadedUri.toString()

                                val reference = database.getReference("posts")
                                val postId = reference.push().key!!
                                reference.child(postId).setValue(post).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }

                if (selectedUri == null) {
                    with(binding){
                        post.text = etCreatePost.text.toString()
                        post.type = type
                        post.time = time
                        post.uid = uid
                        post.postUri = ""
                    }


                    val reference = database.getReference("posts")
                    val postId = reference.push().key!!
                    reference.child(postId).setValue(post).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}