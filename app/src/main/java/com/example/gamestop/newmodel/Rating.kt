package com.example.gamestop.newmodel

import java.io.Serializable

data class Rating(
    val count: Int,
    val id: Int,
    val percent: Double,
    val title: String
): Serializable