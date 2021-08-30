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
import com.zaich.projecttest.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailEditText = findViewById(R.id.registerUsername)
        passwordEditText = findViewById(R.id.registerPassword)
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.registerLoginButton)
        auth = Firebase.auth

        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.registerRepeatPassword.transformationMethod = PasswordTransformationMethod.getInstance()

        binding.loginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.registerRepeatPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.registerRepeatPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = binding.registerRepeatPassword.text.toString()

            if(email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()){
                if (password.equals(confirmPassword)) {
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
                    binding.pbRegisterProfile.visibility = View.INVISIBLE
                }else{
                    binding.pbRegisterProfile.visibility = View.INVISIBLE
                    Toast.makeText(this, "Password and Confirm password not match",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}