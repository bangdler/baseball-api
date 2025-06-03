package com.baseballgame.api.dto

import com.baseballgame.api.domain.History

data class PlayerDto(
    val id: Long,
    val isWinner: Boolean,
    val history: List<History>
)
