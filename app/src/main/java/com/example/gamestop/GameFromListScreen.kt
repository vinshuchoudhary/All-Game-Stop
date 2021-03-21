package com.example.gamestop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gamestop.adapter.PlatformTagAdapter
import com.example.gamestop.adapter.StoreAdapter
import com.example.gamestop.adapter.TestAdapter
import com.example.gamestop.databinding.ActivityGameFromListScreenBinding
import com.example.gamestop.newmodel.ModelClass
import com.example.gamestop.particularmodel.NewModel
import com.example.gamestop.trailermodel.TrailerModel
import com.example.gamestop.videoview.ListGameVideoView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameFromListScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGameFromListScreenBinding
    private lateinit var adapter: TestAdapter
    private lateinit var slug: String
    private lateinit var tagAdapter: PlatformTagAdapter
    private lateinit var storeAdapter: StoreAdapter
    var expanded: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameFromListScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val list = intent.getSerializableExtra("GameFromListScreen") as ModelClass
        val position = intent.getIntExtra("position", 0)
        slug = list.results[position].slug
        gettingFullData(slug)
        Toast.makeText(this, slug, Toast.LENGTH_SHORT).show()
        adapter = TestAdapter(list, this, position)
        binding.screenshotRecyclerView.adapter = adapter
        binding.screenshotRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //getting trailer
        getTrailerLink(slug, this)

        //setting description
        binding.description.setOnClickListener {
            settingDescription()
        }

        binding.expandScreenshot.setOnClickListener {

            if (binding.screenshotRecyclerView.visibility == View.VISIBLE) {
                binding.screenshotArrow.setImageResource(R.drawable.ic_go)
                binding.screenshotRecyclerView.visibility = View.GONE
            } else {
                binding.screenshotArrow.setImageResource(R.drawable.ic_down_arrow)
                binding.screenshotRecyclerView.visibility = View.VISIBLE
            }
        }

        binding.playButton.setOnClickListener {
            if (data.contains("https") || data != "null") {
                val bottomSheetFragment = ListGameVideoView()
                bottomSheetFragment.show(supportFragmentManager, "bottomSheetTag")
            } else {
                Toast.makeText(this, "video not available", Toast.LENGTH_SHORT).show()

            }
        }

        Glide.with(this)
            .load(list.results[position].background_image)
            .into(binding.chosenGameImage)
        binding.collapsingToolBar.title = list.results[position].name
        binding.collapsingToolBar.setCollapsedTitleTextColor(getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(getColor(R.color.white))

    }

    fun gettingFullData(slug: String) {

        val gameInfo = GameObject.gameInstance.getFullData(slug)
        gameInfo.enqueue(object : Callback<NewModel> {
            override fun onResponse(call: Call<NewModel>, response: Response<NewModel>) {

                val gotData = response.body()
                if (gotData != null) {

                    //setting platform tag adapter

                    tagAdapter = PlatformTagAdapter(gotData)
                    binding.tagRecyclerView.adapter = tagAdapter
                    binding.tagRecyclerView.layoutManager = LinearLayoutManager(
                        this@GameFromListScreen,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    //setting store recyclerView
                    storeAdapter = StoreAdapter(gotData, this@GameFromListScreen)
                    binding.storeRecyclerView.adapter = storeAdapter
                    binding.storeRecyclerView.layoutManager = LinearLayoutManager(
                        this@GameFromListScreen,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )


                    var desc = gotData.description
                    desc = android.text.Html.fromHtml(desc).toString()
                    binding.description.text = desc
                    binding.collapsingToolBar.title = gotData.name
                    binding.collapsingToolBar.setExpandedTitleColor(getColor(R.color.white))
                    binding.collapsingToolBar.setCollapsedTitleTextColor(getColor(R.color.white))

                    if ((gotData.platforms[0].requirements.minimum) != null && (gotData.platforms[0].requirements.recommended) != null) {
                        binding.minimumRequirements.text = gotData.platforms[0].requirements.minimum
                        binding.recommendedRequirements.text =
                            gotData.platforms[0].requirements.recommended
                    } else {
                        binding.requirementHeading.visibility = View.GONE
                        binding.requirementLayout.visibility = View.GONE
                    }


                } else {
                    Toast.makeText(this@GameFromListScreen, "data is empty", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<NewModel>, t: Throwable) {
                Toast.makeText(
                    this@GameFromListScreen,
                    "cannot retrieve full game data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    fun settingDescription() {
        if (expanded == 0) {
            binding.description.maxLines = Int.MAX_VALUE
            expanded = 1
        } else {
            binding.description.maxLines = 6
            expanded = 0
        }
    }

    companion object {

        lateinit var data: String

        fun getTrailerLink(slug: String, context: Context) {
            val videoInfo = GameObject.gameInstance.getTrailer(slug)
            videoInfo.enqueue(object : Callback<TrailerModel> {
                override fun onResponse(
                    call: Call<TrailerModel>,
                    response: Response<TrailerModel>
                ) {

                    val gotTrailer = response.body()

                    if (gotTrailer != null) {

                        if (gotTrailer.count != 0) {

                            val videoLink = gotTrailer.results[0].data.max
                            data = videoLink
                            Log.d("vinshu", "passing $videoLink")

                        } else {
                            data = "null"
                        }
                    }
                }

                override fun onFailure(call: Call<TrailerModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        }

    }

}