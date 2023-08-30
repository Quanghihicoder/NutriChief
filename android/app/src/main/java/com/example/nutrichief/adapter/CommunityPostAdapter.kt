package com.example.nutrichief.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CommunityPost
import com.example.nutrichief.view.PostDetailActivity

class CommunityPostAdapter (private var postList: MutableList<CommunityPost>,
                         private val listener: OnItemClickListener
) : RecyclerView.Adapter<CommunityPostAdapter.PostItemViewHolder>(), Filterable {

    // Interface for defining click listener callbacks
    interface OnItemClickListener {
        fun onFoodClick(post: CommunityPost)
        fun onLikeClick(post: CommunityPost)
        fun onDislikeClick(post: CommunityPost)
        fun onCommentClick(post: CommunityPost)
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

        holder.likeBtn.setOnClickListener{

        }

        holder.dislikeBtn.setOnClickListener{

        }

        holder.commentBtn.setOnClickListener{
            listener.onCommentClick(post)
        }
    }

    override fun getItemCount(): Int = postList.size

    inner class PostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val postTitle: TextView = itemView.findViewById(R.id.post_title)
        val postDesc: TextView = itemView.findViewById(R.id.post_desc)
        val postLike: TextView = itemView.findViewById(R.id.post_like_number)
        val likeBtn: ImageView = itemView.findViewById(R.id.post_like)
        val dislikeBtn: ImageView = itemView.findViewById(R.id.post_dislike)
        val commentBtn: ImageView = itemView.findViewById(R.id.post_comment)
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}