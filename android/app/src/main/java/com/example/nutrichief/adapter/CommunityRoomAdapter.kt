package com.example.nutrichief.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CommunityRoom
import com.squareup.picasso.Picasso

class CommunityRoomAdapter (private  var communityRoomList: MutableList<CommunityRoom>)
    : RecyclerView.Adapter<CommunityRoomAdapter.CommunityRoomItemViewHolder>() {

    override fun getItemCount(): Int = communityRoomList.size

    inner class CommunityRoomItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomImg: ImageView = itemView.findViewById(R.id.room_img)
        val roomName: TextView = itemView.findViewById(R.id.room_name)
        val roomMemberCount: TextView = itemView.findViewById(R.id.room_member_count)
        val roomRecentPostCount: TextView = itemView.findViewById(R.id.room_recent_post_count)
        val roomDesc: TextView = itemView.findViewById(R.id.room_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityRoomItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_community_room, parent, false) as View
        return CommunityRoomItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityRoomItemViewHolder, position: Int) {
        val room = communityRoomList[position]
        val imageUrl = room.room_img
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(holder.roomImg)
        } else {
            holder.roomImg.setImageResource(R.drawable.default_item_image)
        }
        holder.roomName.text = room.room_name
        holder.roomMemberCount.text = room.room_member_count.toString() + " members"
        holder.roomRecentPostCount.text = room.room_recent_post_count.toString() + " posts a day"
        holder.roomDesc.text = room.room_desc
    }

}