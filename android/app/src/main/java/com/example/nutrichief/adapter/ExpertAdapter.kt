package com.example.nutrichief.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CommunityPost
import com.example.nutrichief.datamodels.Expert

class ExpertAdapter(private var expertList: MutableList<Expert>): RecyclerView.Adapter<ExpertAdapter.ExpertItemViewHolder>() {

    override fun getItemCount(): Int = expertList.size

    inner class ExpertItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertName: TextView = itemView.findViewById(R.id.expert_name)
        val expertDesc: TextView = itemView.findViewById(R.id.expert_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertAdapter.ExpertItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_item_expert, parent, false,) as View
        return ExpertItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertAdapter.ExpertItemViewHolder, position: Int) {
        val expert = expertList[position]
        holder.expertName.text = expert.expert_name
        holder.expertDesc.text = expert.expert_desc
    }

}