package com.baseballgame.api.service

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.History
import com.baseballgame.api.domain.Player
import com.baseballgame.api.dto.BaseballGameDto
import com.baseballgame.api.dto.PlayerDto
import com.baseballgame.api.repository.BaseballGameRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class BaseballGameService(
    private val baseballGameRepository: BaseballGameRepository
) {
    fun createGame(name: String): BaseballGame {
        val game = BaseballGame(name = name)
        return baseballGameRepository.save(game)
    }

    fun findGame(id: Long): BaseballGameDto {
        val game = baseballGameRepository.findById(id)
            .orElseThrow { NoSuchElementException("Game not found") }

        return BaseballGameDto(
            id = game.id ?: throw IllegalStateException("Game ID is null"),
            name = game.name,
            isEnd = game.isEnd,
            players = game.players.map { player ->
                PlayerDto(
                    id = player.id ?: throw IllegalStateException("Player ID is null"),
                    isWinner = player.isWinner,
                    history = player.history
                )
            }
        )
    }

    fun findAllGames(): List<BaseballGame> =
        baseballGameRepository.findAll().toList()

    fun deleteGame(id: Long) {
        baseballGameRepository.deleteById(id)
    }

    @Transactional
    fun updateGame(gameId: Long, isEnd: Boolean, updatedPlayers: List<PlayerDto>) {
        val game = baseballGameRepository.findById(gameId)
            .orElseThrow { RuntimeException("해당 게임이 없습니다.") }

        game.isEnd = isEnd

        // 기존 플레이어를 모두 제거하고 새로 추가, 영속성 때문에 직접 수정해야 db에 반영이 된다고 함.
        game.players.clear()
        updatedPlayers.forEach { dto ->
            val player = Player(
                id = dto.id,
                isWinner = dto.isWinner,
                game = game,
                history = dto.history.map {
                    History(id = it.id, input = it.input, strike = it.strike, ball = it.ball)
                }.toMutableList()
            )
            game.players.add(player)
        }
    }
}
