package com.example.nutrichief.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CommunityPost

class CommunityPostAdapter (private var postList: MutableList<CommunityPost>,
                         private val listener: OnItemClickListener
) : RecyclerView.Adapter<CommunityPostAdapter.PostItemViewHolder>(), Filterable {

    // Interface for defining click listener callbacks
    interface OnItemClickListener {
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
        holder.postDislike.text = post.post_dislike.toString()

        holder.postDesc.setOnClickListener{
            listener.onCommentClick(post)
        }

        holder.likeBtn.setOnClickListener{
            if (holder.liked) {
                holder.postLike.text = (holder.postLike.text.toString().toInt() - 1).toString()
                holder.liked = false
                holder.postLike.setTypeface(null, Typeface.NORMAL);
            } else if (holder.disliked) {
                holder.disliked = false
                holder.liked = true
                holder.postLike.setTypeface(null, Typeface.BOLD);
                holder.postDislike.setTypeface(null, Typeface.NORMAL);
                holder.postDislike.text = (holder.postDislike.text.toString().toInt() - 1).toString()
                holder.postLike.text = (holder.postLike.text.toString().toInt() + 1).toString()
            } else {
                holder.liked = true
                holder.postLike.setTypeface(null, Typeface.BOLD);
                holder.postLike.text = (holder.postLike.text.toString().toInt() + 1).toString()
            }
        }

        holder.dislikeBtn.setOnClickListener{
            if (holder.disliked) {
//                holder.dislikeBtn.setBackgroundColor(R.color.white)
                holder.postDislike.text = (holder.postDislike.text.toString().toInt() - 1).toString()
                holder.disliked = false
                holder.postDislike.setTypeface(null, Typeface.NORMAL);
            } else if (holder.liked) {
                holder.liked = false
                holder.disliked = true
                holder.postLike.setTypeface(null, Typeface.NORMAL);
                holder.postDislike.setTypeface(null, Typeface.BOLD);
                holder.postLike.text = (holder.postLike.text.toString().toInt() - 1).toString()
                holder.postDislike.text = (holder.postDislike.text.toString().toInt() + 1).toString()
            } else {
                holder.disliked = true
                holder.postDislike.setTypeface(null, Typeface.BOLD);
                holder.postDislike.text = (holder.postDislike.text.toString().toInt() + 1).toString()
            }

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
        val postDislike: TextView = itemView.findViewById(R.id.post_dislike_number)
        val likeBtn: ImageView = itemView.findViewById(R.id.post_like)
        val dislikeBtn: ImageView = itemView.findViewById(R.id.post_dislike)
        val commentBtn: ImageView = itemView.findViewById(R.id.post_comment)
        var liked = false
        var disliked = false
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}