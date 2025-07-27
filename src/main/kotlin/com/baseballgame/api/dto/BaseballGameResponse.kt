package com.baseballgame.api.dto

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.GameStatus

data class BaseballGameResponse(
    val id: Long,
    val name: String,
    val status: GameStatus,
    val players: List<PlayerResponse>,
    val answer: List<Int>,
    val curPlayerIdx: Int,
) {
    companion object {
        fun of(game: BaseballGame): BaseballGameResponse {
            return BaseballGameResponse(
                id = game.id ?: throw IllegalStateException("Game ID is null"),
                name = game.name,
                status = game.status,
                players = game.players.map { PlayerResponse.of(it) },
                answer = game.answer.toList(),
                curPlayerIdx = game.curPlayerIdx
            )
        }
    }
}
