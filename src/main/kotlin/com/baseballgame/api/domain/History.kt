package com.baseballgame.api.domain

data class History(
    val id: Long,
    val input: String,
    val strike: Int,
    val ball: Int
)
