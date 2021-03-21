package com.example.gamestop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamestop.databinding.PlatformTagItemBinding
import com.example.gamestop.particularmodel.NewModel

class PlatformTagAdapter(val list : NewModel) : RecyclerView.Adapter<PlatformTagAdapter.PlatformTagViewHolder>(){

    inner class PlatformTagViewHolder(val binding: PlatformTagItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformTagViewHolder {
        val binding = PlatformTagItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlatformTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlatformTagViewHolder, position: Int) {
        holder.binding.tagText.text = list.parent_platforms[position].platform.name
    }

    override fun getItemCount(): Int {
        return list.parent_platforms.size
    }

}