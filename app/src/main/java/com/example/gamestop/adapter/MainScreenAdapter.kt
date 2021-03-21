package com.example.gamestop.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamestop.GameFromListScreen
import com.example.gamestop.GameObject
import com.example.gamestop.databinding.GameScreenLayoutBinding
import com.example.gamestop.newmodel.ModelClass
import com.example.gamestop.newmodel.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainScreenAdapter(
    val second: MutableList<Result>,
    val context: Context,
    val newList: ModelClass
) :
    RecyclerView.Adapter<MainScreenAdapter.MainScreenViewHolder>() {

    var num=2
    var list: MutableList<Result> = second
    var newSecondList: ArrayList<ModelClass> = ArrayList()
    var loading = false
    var flag = true

    inner class MainScreenViewHolder(val itemBinding: GameScreenLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
//        Log.d("vinshu jaat",newSecondList[0].toString())
        val binding =
            GameScreenLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainScreenViewHolder(binding)
    }

    fun initialize(){
        flag = false
        newSecondList.add(newList)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {

        holder.itemBinding.apply {
            gameCard.setOnClickListener {
                val intent = Intent(context, GameFromListScreen::class.java)
                val passingIndex : Int = position/20
                intent.putExtra("GameFromListScreen", newSecondList[passingIndex])
                val passingPosition = position%20
                intent.putExtra("position", passingPosition)
                context.startActivity(intent)
            }
            ratingCount.text = list[position].ratings_count.toString()
            Glide.with(context)
                .load(list[position].background_image)
                .into(gameImage)
            ratingBar.rating = (list[position].rating).toFloat()
            gameName.text = list[position].name
            launchDate.text = list[position].released
            genreOne.text = list[position].genres[0].name
            if (list[position].genres.lastIndex >= 1) {
                genreTwo.text = list[position].genres[1].name
            } else {
                genreTwo.visibility = View.GONE

            }
            if (list[position].genres.lastIndex >= 2) {
                genreThree.text = list[position].genres[2].name
            } else {
                genreThree.visibility = View.GONE
            }
        }

        if(position == list.size-5){
            pagination()
            loading = true
        }

        if(position == list.size-1 && loading){
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
            if(flag){
                initialize()
            }
            return list.size
    }

    fun pagination(){

        val temp = GameObject.gameInstance.getGame(num)
        temp.enqueue(object : Callback<ModelClass> {
            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                val new = response.body()
                if (new != null) {
                    val vinshu = new.results.toMutableList()
                    list.addAll(vinshu)
                    notifyDataSetChanged()
                    loading = false
                    num++
                    newSecondList.add(new)
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Log.d("vinshu","something happened $")
            }
        })
    }

}