package com.example.howtofail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class HostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var dbRef: DatabaseReference
    lateinit var dbRef2: DatabaseReference
    lateinit var user : MutableList<User>

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

        val rBtn = findViewById<Button>(R.id.rBtn)

        rBtn.setOnClickListener {
            retrieveData()
        }
        
    }

    private fun doTheTestUpload(){
        val uBtn = findViewById<Button>(R.id.uBtn)
        val name = findViewById<EditText>(R.id.tName)
        val hobby = findViewById<EditText>(R.id.tHobby)

        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef2 = FirebaseDatabase.getInstance().getReference("stories")

        uBtn.setOnClickListener {
            val testObj = User(name.text.toString(),hobby.text.toString())
            dbRef.push().setValue(testObj).addOnCompleteListener {
                Toast.makeText(this,"Done ${auth.currentUser!!.uid}",Toast.LENGTH_SHORT).show()
            }
            val testObj2=User("mike","writing")
            dbRef2.push().setValue(testObj2).addOnCompleteListener {
                Toast.makeText(this,"Done for stories",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun retrieveData(){
        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef2 = FirebaseDatabase.getInstance().getReference("stories")
        user = mutableListOf()
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                for(child in snapshot.children){
                    val us = child.getValue(User::class.java)
                    user.add(us!!)
                }
            }}
        })

        Toast.makeText(this,"${user[0].name},${user[1].name}",Toast.LENGTH_LONG).show()
    }
}