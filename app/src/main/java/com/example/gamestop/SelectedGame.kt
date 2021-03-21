package com.example.gamestop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bumptech.glide.Glide
import com.example.gamestop.adapter.PlatformTagAdapter
import com.example.gamestop.adapter.StoreAdapter
import com.example.gamestop.adapter.TestAdapter
import com.example.gamestop.databinding.ActivitySelectedGameBinding
import com.example.gamestop.newmodel.ModelClass
import com.example.gamestop.particularmodel.NewModel
import com.example.gamestop.trailermodel.TrailerModel
import com.example.gamestop.videoview.BottomSheetFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectedGame : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedGameBinding
    lateinit var slug: String
    var position: Int = 0
    private lateinit var adapter: TestAdapter
    private lateinit var tagAdapter: PlatformTagAdapter
    private lateinit var storeAdapter: StoreAdapter
    var expanded: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val list = intent.getSerializableExtra("vinshu") as ModelClass
        position = intent.getIntExtra("vinshu position", 1)
        slug = list.results[position].slug
        gettingFullData(slug)
        getTrailerLink(slug, this)

        binding.description.setOnClickListener {
            settingDescription()
        }

        Glide.with(this)
            .load(list.results[position].background_image)
            .into(binding.chosenGameImage)

        binding.playButton.setOnClickListener {
            if (data.contains("https") || data != "null") {
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(supportFragmentManager, "bottomSheetTag")
            } else {
                Toast.makeText(this, "video not available", Toast.LENGTH_SHORT).show()

            }
        }

        adapter = TestAdapter(list, this, position)
        binding.screenshotRecyclerView.adapter = adapter
        binding.screenshotRecyclerView.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)

        binding.expandScreenshot.setOnClickListener {

            if (binding.screenshotRecyclerView.visibility == View.VISIBLE) {
                binding.screenshotArrow.setImageResource(R.drawable.ic_go)
                binding.screenshotRecyclerView.visibility = View.GONE
            } else {
                binding.screenshotArrow.setImageResource(R.drawable.ic_down_arrow)
                binding.screenshotRecyclerView.visibility = View.VISIBLE
            }
        }

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
                        this@SelectedGame,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    //setting store recyclerView
                    storeAdapter = StoreAdapter(gotData, this@SelectedGame)
                    binding.storeRecyclerView.adapter = storeAdapter
                    binding.storeRecyclerView.layoutManager = LinearLayoutManager(
                        this@SelectedGame,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )


                    var desc = gotData.description
                    desc = android.text.Html.fromHtml(desc).toString()
                    binding.description.text = desc
                    binding.selectedScreenToolBar.title = gotData.name
                    binding.selectedScreenToolBar.setExpandedTitleColor(getColor(R.color.white))
                    binding.selectedScreenToolBar.setCollapsedTitleTextColor(getColor(R.color.white))
                    if ((gotData.platforms[0].requirements.minimum) != null && (gotData.platforms[0].requirements.recommended) != null) {
                        binding.minimumRequirements.text = gotData.platforms[0].requirements.minimum
                        binding.recommendedRequirements.text =
                            gotData.platforms[0].requirements.recommended
                    } else {
                        binding.requirementHeading.visibility = View.GONE
                        binding.requirementLayout.visibility = View.GONE
                    }


                } else {
                    Toast.makeText(this@SelectedGame, "data is empty", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<NewModel>, t: Throwable) {
                Toast.makeText(this@SelectedGame, "couldn't get full data", Toast.LENGTH_SHORT)
                    .show()
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