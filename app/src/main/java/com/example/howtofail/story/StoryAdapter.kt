package com.example.howtofail.story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.howtofail.R
import com.example.howtofail.Story

class StoryAdapter(val stories: List<Story>, val activity: StoryActivity, val listener: OnItemClickListener):RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_list,parent,false)
        return StoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val currStory = stories[position]

        with(holder){
            title.text = currStory.title
            content.text = currStory.content
            date.text = currStory.date
            story_author_name.text = currStory.author_name

            Glide.with(activity).load(currStory.imageUrl).into(img)
        }
    }

   inner class StoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val title = itemView.findViewById<TextView>(R.id.story_title)
        val content = itemView.findViewById<TextView>(R.id.story_content)
        val date = itemView.findViewById<TextView>(R.id.story_date)
        val img = itemView.findViewById<ImageView>(R.id.story_image)
        val story_author_name = itemView.findViewById<TextView>(R.id.story_author_name)

       init{
           itemView.setOnClickListener(this)
       }

       override fun onClick(p0: View?) {
           val position = adapterPosition
           if(position!=RecyclerView.NO_POSITION){
               listener.onItemClick(stories[position])
           }
       }
    }

    interface OnItemClickListener{
        fun onItemClick(st: Story)
    }
}