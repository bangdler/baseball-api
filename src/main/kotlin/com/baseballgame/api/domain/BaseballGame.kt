package com.baseballgame.api.domain

class BaseballGame(
    val id: Long? = null,
    val name: String,
    var status: GameStatus = GameStatus.IDLE,
    val players: List<Player> = emptyList(),
    var curPlayerIdx: Int = 0,
    val answer: BaseballNumber
)