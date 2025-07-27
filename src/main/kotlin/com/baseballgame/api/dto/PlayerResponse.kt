package com.baseballgame.api.dto

import com.baseballgame.api.domain.History
import com.baseballgame.api.domain.Player

data class PlayerResponse(
    val id: Long,
    val isWinner: Boolean,
    val history: List<History>
) {
    companion object {
        fun of(player: Player): PlayerResponse =
            PlayerResponse(
                id = player.id ?: throw IllegalStateException("Player ID is null"),
                isWinner = player.isWinner,
                history = player.history
            )
    }
}
