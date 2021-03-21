package com.example.gamestop.newmodel

import java.io.Serializable

data class Genre(
    val games_count: Int,
    val id: Int,
    val image_background: String,
    val name: String,
    val slug: String
): Serializable