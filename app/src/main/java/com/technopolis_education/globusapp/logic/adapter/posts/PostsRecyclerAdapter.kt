package com.technopolis_education.globusapp.logic.adapter.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R

class PostsRecyclerAdapter() : RecyclerView.Adapter<PostsRecyclerAdapter.PostViewHolder>() {

//    private var posts = emptyList<>()

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView? = null
        var content: TextView? = null
        var creator: ImageView? = null

        init {
            title = itemView.findViewById(R.id.post_title)
            content = itemView.findViewById(R.id.post_content)
            creator = itemView.findViewById(R.id.post_avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.feed_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        holder.title?.text = posts[position].title
//        holder.content?.text = posts[position].content
//        holder.creator?.setBackgroundResource(R.drawable.ic_launcher_background)
    }

    override fun getItemCount(): Int {
//        return posts.size
        return 1
    }

//    fun setData(posts: List<Post>) {
//        this.posts = posts
//        notifyDataSetChanged()
//    }
}
