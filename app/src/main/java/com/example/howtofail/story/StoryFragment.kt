package com.example.howtofail.story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.howtofail.R
import com.example.howtofail.Story
import com.google.firebase.database.*


class StoryFragment : Fragment(),StoryAdapter.OnItemClickListener {

    lateinit var dbRef: DatabaseReference
    lateinit var storylist : MutableList<Story>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbRef = FirebaseDatabase.getInstance().getReference().child("stories")
        storylist = mutableListOf()

        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Please check your internet connection",Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(eachChild in snapshot.children){
                        val story = eachChild.getValue(Story::class.java)
                        storylist.add(story!!)
                    }
                }
                else{
                    Toast.makeText(activity,"No stories to display",Toast.LENGTH_LONG).show()
                }

                storylist.reverse()
                setRecyclerView(storylist)
            }

        })

    }

    private fun setRecyclerView( stories: MutableList<Story>){
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.story_recy)
        val newAdapter = StoryAdapter(stories, requireActivity() as StoryActivity, this@StoryFragment)
        recyclerView.adapter = newAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onItemClick(st: Story) {
        val action = StoryFragmentDirections.actionStoryFragmentToContentStoryFragment(st)
        findNavController().navigate(action)
    }

}