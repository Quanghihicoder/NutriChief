package com.example.nutrichief.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CommunityPost

class CommunityPostAdapter (private var postList: MutableList<CommunityPost>,
//                            private val listener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<CommunityPostAdapter.PostItemViewHolder>(), Filterable {
    private var communityPostList = ArrayList<CommunityPost>(postList)

    // Interface for defining click listener callbacks
    interface OnItemClickListener {
        fun onFoodClick(item: CommunityPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostAdapter.PostItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_post, parent, false,) as View
        return PostItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val post = postList[position]
        holder.userName.text = post.user_name
        holder.postTitle.text = post.post_title
        holder.postDesc.text = post.post_detail
        holder.postLike.text = post.post_like.toString()

        holder.itemView.setOnClickListener{
//            listener.onFoodClick(food)
        }
    }

    override fun getItemCount(): Int = postList.size

    inner class PostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val postTitle: TextView = itemView.findViewById(R.id.post_title)
        val postDesc: TextView = itemView.findViewById(R.id.post_desc)
        val postLike: TextView = itemView.findViewById(R.id.post_like_number)
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}