package com.example.howtofail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var dbRef: DatabaseReference
    lateinit var dbRef2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val lgBtn = findViewById<Button>(R.id.logOut)
        val tview = findViewById<TextView>(R.id.testView)
        auth = FirebaseAuth.getInstance()
        var temp = auth.currentUser!!.email
        temp = "You are logged in as: "+ temp
        tview.text = temp

        lgBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        doTheTestUpload()
    }

    private fun doTheTestUpload(){
        val uBtn = findViewById<Button>(R.id.uBtn)
        val name = findViewById<EditText>(R.id.tName)
        val hobby = findViewById<EditText>(R.id.tHobby)

        val Name = name.text.toString()
        val Hobby = hobby.text.toString()
        //val testObj = User(Name,Hobby)
        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef2 = FirebaseDatabase.getInstance().getReference("stories")

        uBtn.setOnClickListener {
            var id = dbRef.push().key!!
            val testObj = User("saumi","football")
            dbRef.push().setValue(testObj).addOnCompleteListener {
                Toast.makeText(this,"Done ${auth.currentUser!!.uid}",Toast.LENGTH_SHORT).show()
            }
            val testObj2=User("mike","writing")
            dbRef2.push().setValue(testObj2).addOnCompleteListener {
                Toast.makeText(this,"Done for stories ${auth.currentUser!!.uid}",Toast.LENGTH_SHORT).show()
            }
        }




    }
}