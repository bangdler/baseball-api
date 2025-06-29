package com.baseballgame.api.service

import com.baseballgame.api.domain.*
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
        val answer = createRandomBaseballNumber()
        val game = BaseballGame(name = name, answer = answer, curPlayerIdx = 0)

        return baseballGameRepository.save(game)
    }

    fun findGame(id: Long): BaseballGameDto {
        val game = baseballGameRepository.findById(id)
            .orElseThrow { NoSuchElementException("Game not found") }

        return BaseballGameDto(
            id = game.id ?: throw IllegalStateException("Game ID is null"),
            name = game.name,
            status = game.status,
            players = game.players.map { player ->
                PlayerDto(
                    id = player.id ?: throw IllegalStateException("Player ID is null"),
                    isWinner = player.isWinner,
                    history = player.history
                )
            },
            answer = listOf(game.answer.number1, game.answer.number2, game.answer.number3),
            curPlayerIdx = game.curPlayerIdx
        )
    }

    fun findAllGames(): List<BaseballGame> =
        baseballGameRepository.findAll().toList()

    fun deleteGame(id: Long) {
        baseballGameRepository.deleteById(id)
    }

    @Transactional
    fun updateGame(gameId: Long, status: GameStatus, updatedPlayers: List<PlayerDto>, newPlayerIdx: Int) {
        val game = baseballGameRepository.findById(gameId)
            .orElseThrow { RuntimeException("해당 게임이 없습니다.") }

        game.status = status
        game.curPlayerIdx = newPlayerIdx

        // 기존 플레이어를 ID로 매핑
        val existingPlayers = game.players.associateBy { it.id }

        // 업데이트된 플레이어 처리
        updatedPlayers.forEach { dto ->
            val player = existingPlayers[dto.id]
            if (player != null) {
                // 기존 플레이어 업데이트
                player.isWinner = dto.isWinner
                player.history.clear()
                player.history.addAll(
                    dto.history.map {
                        History(id = it.id, input = it.input, strike = it.strike, ball = it.ball)
                    }
                )
            } else {
                // 새 플레이어 추가
                val newPlayer = Player(
                    id = dto.id,
                    isWinner = dto.isWinner,
                    game = game,
                    history = dto.history.map {
                        History(id = it.id, input = it.input, strike = it.strike, ball = it.ball)
                    }.toMutableList()
                )
                game.players.add(newPlayer)
            }
        }

        // 제거된 플레이어 삭제 (orphanRemoval=true로 인해 자동 처리됨)
        val playersToRemove = existingPlayers.keys - updatedPlayers.map { it.id }.toSet()
        game.players.removeIf { it.id in playersToRemove }
    }


    private fun createRandomBaseballNumber(): BaseballNumber {
        val numbers = (1..9).shuffled().take(3)
        return BaseballNumber(
            number1 = numbers[0],
            number2 = numbers[1],
            number3 = numbers[2]
        )
    }
}
