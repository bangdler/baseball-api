package com.baseballgame.api.dto

data class PlayerDto(
    val id: Long? = null,
    val isWinner: Boolean,
    val history: List<HistoryDto>
)
