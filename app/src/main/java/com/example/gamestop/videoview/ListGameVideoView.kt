package com.example.gamestop.videoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gamestop.GameFromListScreen
import com.example.gamestop.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.potyvideo.library.AndExoPlayerView

class ListGameVideoView : BottomSheetDialogFragment() {

    lateinit var myVideoView: AndExoPlayerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.video_view, container, false)

        myVideoView = view.findViewById(R.id.myVideoView)

        val link = GameFromListScreen.data
        myVideoView.setSource(link)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}