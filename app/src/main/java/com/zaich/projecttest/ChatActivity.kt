package com.zaich.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "EXTRA USER"
    }

    //    private var firebaseUser : FirebaseUser? = null
//    private var reference : DatabaseReference? = null
    private lateinit var fireStore : FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = Firebase.firestore
        auth = Firebase.auth

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

        val username = intent.getStringExtra(EXTRA_USER)
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        binding.tvUserName.text = username

//        getChat()



/*        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(selectUser.uid!!)

        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Profile::class.java)
                binding.tvUserName.text = user?.name
                if (user?.url == ""){
                    binding.imgProfile.setImageResource(R.mipmap.ic_launcher_round)
                }else{
                    Glide.with(this@ChatActivity).load(user?.url).into(binding.imgProfile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })*/
    }

/*    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser


        val selectUser = intent.getParcelableExtra<Profile>(EXTRA_USER) as Profile
        currentUser?.let {
            val uid = it.uid
            val reference = fireStore.collection("users").document(uid)
            reference = FirebaseDatabase.getInstance().getReference("users").child(selectUser.uid!!)
            reference?.get()?.addOnCompleteListener {
                it.result?.let {
                    if (it.exists()){
                        Glide.with(this).load(selectUser.url).into(binding.imgProfile)
                        binding.tvUserName.text = selectUser.name
                    }
                    else{
                        Glide.with(this@ChatActivity).load(selectUser.url).into(binding.imgProfile)
                    }
                }
            }
        }
    }*/

}