package com.baseballgame.api.domain

class Player(
    val id: Long,
    var isWinner: Boolean,
    val history: List<History> = emptyList(),
    val game: BaseballGame
)