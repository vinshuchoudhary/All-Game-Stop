package com.example.gamestop.trailermodel

data class TrailerModel(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)