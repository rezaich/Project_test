package com.zaich.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.registerUsername)
        passwordEditText = findViewById(R.id.registerPassword)
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.registerLoginButton)
        auth = Firebase.auth

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            val error = it.exception
                            Toast.makeText(this, "error: ${error}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}