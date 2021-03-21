package com.example.gamestop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.gamestop.databinding.ActivityMainBinding
import com.example.gamestop.adapter.MainScreenAdapter
import com.example.gamestop.newmodel.ModelClass
import com.example.gamestop.newmodel.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MainScreenAdapter
    lateinit var linearLayoutManager: ZoomRecyclerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.progressBar.visibility = View.VISIBLE

        getGame(1)

        binding.goButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.focusable
            val gameName = binding.searchView.text.toString()
            getOneGame(gameName)
        }


    }

    private fun getOneGame(gameName: String) {

        val oneGame = GameObject.gameInstance.getOneGame(gameName)
        oneGame.enqueue(object : Callback<ModelClass> {
            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                val gotGame = response.body()
                binding.progressBar.visibility = View.GONE
                if (gotGame != null) {

                    val intent = Intent(this@MainActivity, NextScreen::class.java)
                    intent.putExtra("ser", gotGame)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@MainActivity, "gotGame is null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failed to retrieve data", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    fun getGame(num: Int) {

        val game = GameObject.gameInstance.getGame(num)

        game.enqueue(object : Callback<ModelClass> {
            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                val gotGame = response.body()
                binding.progressBar.visibility = View.GONE
                if (gotGame != null) {
                    val passing: MutableList<Result> = gotGame.results.toMutableList()
                    adapter = MainScreenAdapter(passing, this@MainActivity, gotGame)
                    binding.myRecyclerView.adapter = adapter
                    linearLayoutManager = ZoomRecyclerLayout(this@MainActivity)

                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    val snapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(binding.myRecyclerView)
                    binding.myRecyclerView.isNestedScrollingEnabled = false
                    binding.myRecyclerView.layoutManager = linearLayoutManager
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Toast.makeText(this@MainActivity, "an error occurred", Toast.LENGTH_SHORT).show()
            }
        })

    }

}