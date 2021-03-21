package com.example.gamestop.newmodel

import java.io.Serializable

data class AddedByStatus(
    val beaten: Int,
    val dropped: Int,
    val owned: Int,
    val playing: Int,
    val toplay: Int,
    val yet: Int
) :Serializable