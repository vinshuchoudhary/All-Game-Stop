package com.example.gamestop.newmodel

import java.io.Serializable

data class EsrbRating(
    val id: Int,
    val name: String,
    val slug: String
): Serializable