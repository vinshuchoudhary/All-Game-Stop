package com.example.gamestop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamestop.databinding.TestItemBinding
import com.example.gamestop.newmodel.ModelClass
import com.example.gamestop.ScreenShotActivity


class TestAdapter(val list: ModelClass, val context: Context, val index: Int) :
    RecyclerView.Adapter<TestAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: TestItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context)
            .load(list.results[index].short_screenshots[position].image)
            .into(holder.binding.testImage)

        holder.binding.testImage.setOnClickListener {
            val intent = Intent(context, ScreenShotActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("position", position)
            intent.putExtra("list", list)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        val len = list.results[index].short_screenshots.size
        return len
    }

}