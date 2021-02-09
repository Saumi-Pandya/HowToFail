package com.example.howtofail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    lateinit var regBtn: Button
    lateinit var loginBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        regBtn = findViewById(R.id.registerBtn)
        loginBtn = findViewById(R.id.loginBtn)

        regBtn.setOnClickListener {
            sendToRegisterScreen()
        }

        loginBtn.setOnClickListener {
            verifyAndMoveToHomeFrag()
        }
    }

    private fun sendToRegisterScreen(){
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun verifyAndMoveToHomeFrag(){

    }
}