package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zaich.projecttest.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailEditText = findViewById(R.id.loginUsername)
        passwordEditText = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.loginRegisterButton)
        auth = Firebase.auth
        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()



        binding.loginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            val email=emailEditText.text.toString()
            val password=passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                binding.pbRegisterProfile.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val uid = it.result!!.user!!.uid
                            //request ke endpoinr Laravel login firebase
                            //parameter yang berisi token(firebase.token)
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                    }else{
                        val error = it.exception
                            Toast.makeText(this, "ErrorL ${error}", Toast.LENGTH_SHORT).show()
                        }
                }
                binding.pbRegisterProfile.visibility = View.INVISIBLE
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}