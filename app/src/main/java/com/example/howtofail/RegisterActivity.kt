package com.example.howtofail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val email = findViewById<EditText>(R.id.registerEmail)
        val pass = findViewById<EditText>(R.id.registerPass)
        val cpass = findViewById<EditText>(R.id.registerCPass)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val progBar = findViewById<ProgressBar>(R.id.progressBar)

        registerBtn.setOnClickListener {
            if(verifyFields(email.text.toString(),pass.text.toString(),cpass.text.toString())){

                progBar.visibility = View.VISIBLE
                val currentuser = auth.currentUser
                if(currentuser!=null){
                    Toast.makeText(this,"User Already Exists",Toast.LENGTH_SHORT).show()
                    progBar.visibility = View.GONE
                    auth.signOut()
                }

                else{
                    Toast.makeText(this,"Block ",Toast.LENGTH_SHORT).show()
                   auth.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener(this){task->
                       Toast.makeText(this,"${pass.text.toString()}",Toast.LENGTH_SHORT).show()
                       if(task.isSuccessful){
                           Toast.makeText(this,"Your account has been created successfully",Toast.LENGTH_SHORT).show()
                           progBar.visibility = View.GONE
                           val intent = Intent(this,HostActivity::class.java)
                           startActivity(intent)
                           finish()
                       }
                       else{
                           Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show()
                           progBar.visibility = View.GONE
                       }
                   }
                }

            }
        }

    }

    private fun verifyFields(email: String,pass: String, cpass: String): Boolean{
        val emLayout = findViewById<TextInputLayout>(R.id.registerEmLayout)
        val cLayout = findViewById<TextInputLayout>(R.id.registerPaLayout)
        val cpLayout = findViewById<TextInputLayout>(R.id.registerCPaLayout)
        emLayout.error = null
        cLayout.error = null
        cpLayout.error = null
        val str = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if(email.isEmpty() or pass.isEmpty() or cpass.isEmpty()){
            Toast.makeText(this,"Please fill all three fields",Toast.LENGTH_SHORT).show()
            return false
        }
        else if(!pass.equals(cpass)){
            cpLayout.error = "Passwords do not match"
            return false
        }

        else if(!str.toRegex().matches(email)){
             emLayout.error = "Please provide a valid email address"
            return false
        }
        return true
    }
}