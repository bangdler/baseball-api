package com.baseballgame.api.dto

import com.baseballgame.api.domain.GameStatus

data class BaseballGameDto(
    val id: Long,
    val name: String,
    val status: GameStatus,
    val players: List<PlayerDto>,
    val answer: List<Int>,
    val curPlayerIdx: Int,
)
