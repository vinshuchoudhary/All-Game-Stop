package com.example.gamestop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosphere.fadingedgelayout.FadingEdgeLayout
import com.bumptech.glide.Glide
import com.example.gamestop.SelectedGame
import com.example.gamestop.databinding.GameSearchLayoutBinding
import com.example.gamestop.newmodel.ModelClass

class SearchGameAdapter(val context: Context, val list: ModelClass) :
    RecyclerView.Adapter<SearchGameAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val binding: GameSearchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            GameSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        Glide.with(context)
            .load(list.results[position].background_image)
            .into(holder.binding.gameImage)

        holder.binding.gameName.text = list.results[position].name

        holder.binding.nextScreenDescription.text = list.seo_description
        holder.binding.nextScreenRatingBar.rating = list.results[position].rating.toFloat()

        holder.binding.apply {
            gameName.text = list.results[position].name

            val mFadingEdgeLayout: FadingEdgeLayout = holder.binding.fadingEdgeLayout
            mFadingEdgeLayout.setFadeEdges(true, true, true, true)
            mFadingEdgeLayout.setFadeSizes(20, 20, 5000, 20);
        }

        holder.binding.gameImage.setOnClickListener {
            val intent = Intent(context, SelectedGame::class.java)
            intent.putExtra("vinshu", list)
            intent.putExtra("vinshu position", position)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.results.size
    }

}