package com.example.gamestop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gamestop.databinding.ActivityScreenShotBinding
import com.example.gamestop.newmodel.ModelClass


class ScreenShotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScreenShotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityScreenShotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent=getIntent()
        val list=intent.getSerializableExtra("list") as ModelClass
        val position = intent.getIntExtra("index", 1)
        val index = intent.getIntExtra("position", 0)
        Glide.with(this)
            .load(list.results[position].short_screenshots[index].image)
            .into(binding.screenshotImage)


    }
}