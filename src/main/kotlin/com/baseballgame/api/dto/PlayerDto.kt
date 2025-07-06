package com.baseballgame.api.dto

import com.baseballgame.api.domain.History
import com.baseballgame.api.domain.Player

data class PlayerDto(
    val id: Long,
    val isWinner: Boolean,
    val history: List<History>
) {
    companion object {
        fun from(domain: Player): PlayerDto =
            PlayerDto(
                id = domain.id ?: throw IllegalStateException("Player ID is null"),
                isWinner = domain.isWinner,
                history = domain.history
            )
    }
}
