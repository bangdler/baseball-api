package com.baseballgame.api.dto

data class BaseballGameDto(
    val id: Long,
    val name: String,
    val isEnd: Boolean,
    val players: List<PlayerDto>
)
