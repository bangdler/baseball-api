package com.baseballgame.api.dto

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.GameStatus

data class BaseballGameDto(
    val id: Long,
    val name: String,
    val status: GameStatus,
    val players: List<PlayerDto>,
    val answer: List<Int>,
    val curPlayerIdx: Int,
) {
    companion object {
        fun from(domain: BaseballGame): BaseballGameDto =
            BaseballGameDto(
                id = domain.id ?: throw IllegalStateException("Game ID is null"),
                name = domain.name,
                status = domain.status,
                players = domain.players.map { PlayerDto.from(it) },
                answer = domain.answer.toList(),
                curPlayerIdx = domain.curPlayerIdx
            )
    }
}
