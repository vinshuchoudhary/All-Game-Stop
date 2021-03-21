package com.example.gamestop.newmodel

import java.io.Serializable

data class Platform(
    val id: Int,
    val name: String,
    val slug: String
): Serializable