package com.example.gamestop.newmodel

import java.io.Serializable

data class Filters(
    val years: List<Year>
): Serializable