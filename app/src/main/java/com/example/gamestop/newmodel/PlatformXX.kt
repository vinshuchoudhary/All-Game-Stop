package com.example.gamestop.newmodel

import java.io.Serializable

data class PlatformXX(
    val games_count: Int,
    val id: Int,
    val image: Any,
    val image_background: String,
    val name: String,
    val slug: String,
    val year_end: Any,
    val year_start: Any
): Serializable