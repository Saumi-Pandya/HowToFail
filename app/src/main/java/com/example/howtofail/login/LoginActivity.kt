package com.example.howtofail.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import com.example.howtofail.HostActivity
import com.example.howtofail.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var regBtn: Button
    lateinit var loginBtn: Button
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mytoolbar = findViewById<Toolbar>(R.id.mytoolbar)
        supportActionBar!!.hide()



        regBtn = findViewById(R.id.registerBtn)
        loginBtn = findViewById(R.id.loginBtn)
        auth = FirebaseAuth.getInstance()

        //checking if the user is logged in or not
        if(auth.currentUser!=null){
            val intent = Intent(this, HostActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{

        regBtn.setOnClickListener {
            sendToRegisterScreen()
        }

        loginBtn.setOnClickListener {
            verifyAndMoveToHomeFrag()
        }
    }}

    //sending user to registration screen
    private fun sendToRegisterScreen(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun verifyAndMoveToHomeFrag(){
        val email = findViewById<EditText>(R.id.loginEmail)
        val pass = findViewById<EditText>(R.id.loginPass)
        val intent = Intent(this, HostActivity::class.java)


        if(verifyFields(email.text.toString(),pass.text.toString())){
            auth.signInWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener { task->
                if(task.isSuccessful){
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verifyFields(email:String,pass:String):Boolean{
        val emLayout = findViewById<TextInputLayout>(R.id.loginEmLayout)
        val paLayout = findViewById<TextInputLayout>(R.id.loginPaLayout)
        val str = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        emLayout.error = null
        paLayout.error = null
        if(email.isEmpty() or pass.isEmpty()){
            Toast.makeText(this,"Please fill both the fields",Toast.LENGTH_SHORT).show()
            return false
        }
        else if(!str.toRegex().matches(email)){
            emLayout.error = "Please provide a valid email address"
            return false
        }
        return true
    }
}