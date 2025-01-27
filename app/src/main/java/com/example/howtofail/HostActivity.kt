package com.example.howtofail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.example.howtofail.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var dbRef: DatabaseReference
    lateinit var dbRef2: DatabaseReference
    lateinit var storylist : MutableList<Story>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

      //  supportActionBar!!.hide()
        val lgBtn = findViewById<Button>(R.id.logOut)
        val tview = findViewById<TextView>(R.id.testView)
        auth = FirebaseAuth.getInstance()
        var temp = auth.currentUser!!.email
        temp = "You are logged in as: "+ temp
        tview.text = temp

        lgBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        doTheTestUpload()

        val rBtn = findViewById<Button>(R.id.rBtn)

        rBtn.setOnClickListener {
            retrieveData()
        }

        val imgBtn = findViewById<Button>(R.id.imgBtn)

        imgBtn.setOnClickListener {
            getImagefromDatabase()
        }
    }

    //code to do the test upload
    private fun doTheTestUpload(){
        val uBtn = findViewById<Button>(R.id.uBtn)


        dbRef2 = FirebaseDatabase.getInstance().getReference("stories")

        uBtn.setOnClickListener {
          //  val testObj = Story(title.text.toString(),content.text.toString())
           // dbRef2.child("1").setValue(testObj).addOnCompleteListener {
            //    Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
            //}
            dbRef2.child("1").child("title").setValue("Updated story title")
            dbRef2.child("1").child("upvotes").setValue("Number of upvotes")
            Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
        }

    }

    //code to do the test retrieval
    private fun retrieveData(){
        dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef2 = FirebaseDatabase.getInstance().getReference("stories")
        storylist = mutableListOf()
        var counter = 0
        dbRef2.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                //counter++
               // Toast.makeText(this@HostActivity,"Cancelled",Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                for(child in snapshot.children){
                    val st  = child.getValue(Story::class.java)
                    storylist.add(st as Story)
                }
                    callback(storylist.size)
            }}

        })

    }

    private fun callback(sz: Int){
        Toast.makeText(this@HostActivity,"${sz}",Toast.LENGTH_LONG).show()
    }


    private fun getImagefromDatabase(){
        dbRef2 = FirebaseDatabase.getInstance().getReference("stories").child("1")
        val iview = findViewById<ImageView>(R.id.iview)

        dbRef2.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val thisStory = snapshot.getValue(Story::class.java)
                    val imgUrl = thisStory!!.imageUrl

                    Glide.with(this@HostActivity).load(imgUrl).into(iview)
                }
            }

        })
    }
}