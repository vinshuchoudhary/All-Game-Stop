package com.example.gamestop.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gamestop.databinding.StoreItemBinding
import com.example.gamestop.particularmodel.NewModel

class StoreAdapter(val list: NewModel,val context: Context) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    inner class StoreViewHolder(val binding: StoreItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.binding.storeName.text = list.stores[position].store.name + "\n Click To Visit"

        holder.binding.storeName.setOnClickListener {
            if (list.stores[position].store.domain != "") {
                val url = "https://" + list.stores[position].store.domain
                val intent = Intent(Intent.ACTION_VIEW,Uri.parse(url))
                context.startActivity(intent)
            }else{
                Toast.makeText(context, "Link not available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return list.stores.size
    }

}