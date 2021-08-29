package com.zaich.projecttest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.zaich.projecttest.databinding.ActivitySplashScreenBinding
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth
    private val animationTime:Long=2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        auth = Firebase.auth

        val animatorY = ObjectAnimator.ofFloat(binding.imageView,"y",400f)
        val animatorX = ObjectAnimator.ofFloat(binding.splashTitleTextView,"y",400f)

        animatorY.setDuration(animationTime)
        animatorX.setDuration(animationTime)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animatorY,animatorX)
        animatorSet.start()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if(currentUser != null){
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
        },4000)
    }
}