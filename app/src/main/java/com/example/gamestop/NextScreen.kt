package com.example.gamestop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.gamestop.adapter.SearchGameAdapter
import com.example.gamestop.databinding.ActivityNextScreenBinding
import com.example.gamestop.newmodel.ModelClass
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class NextScreen : AppCompatActivity() {

    private lateinit var adapter: SearchGameAdapter
    private lateinit var binding: ActivityNextScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()

        val list = intent.getSerializableExtra("ser") as ModelClass
        adapter = SearchGameAdapter(this, list)
        val linearLayoutManager = ZoomRecyclerLayout(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.nextScreenRecyclerView)
        binding.nextScreenRecyclerView.adapter = adapter
        binding.nextScreenRecyclerView.layoutManager = linearLayoutManager


    }
}