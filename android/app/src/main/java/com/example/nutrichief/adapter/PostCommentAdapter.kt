package com.example.nutrichief.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CommunityPost
import com.example.nutrichief.datamodels.PostComment

class PostCommentAdapter (private var commentList: MutableList<PostComment>)
    : RecyclerView.Adapter<PostCommentAdapter.CommentItemViewHolder>() {

    override fun getItemCount(): Int = commentList.size

    inner class CommentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.post_comment_user_name)
        val postDetail: TextView = itemView.findViewById(R.id.post_comment_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_post_comment, parent, false,) as View
        return CommentItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val comment = commentList[position]
        holder.userName.text = comment.user_name
        holder.postDetail.text = comment.comment_detail
    }
}