package com.baseballgame.api.service

import HistoryEntity
import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.BaseballNumber
import com.baseballgame.api.domain.GameStatus
import com.baseballgame.api.dto.*
import com.baseballgame.api.entity.BaseballGameEntity
import com.baseballgame.api.entity.PlayerEntity
import com.baseballgame.api.repository.BaseballGameRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class BaseballGameService(
    private val baseballGameRepository: BaseballGameRepository
) {
    fun createGame(request: BaseballGameCreateRequest): BaseballGameResponse {
        val answer = createRandomBaseballNumber()
        val game = BaseballGame(name = request.name, answer = answer)

        val entity = BaseballGameEntity.from(domain = game)
        val savedEntity = baseballGameRepository.save(entity)

        return BaseballGameResponse.of(game = savedEntity.toDomain())
    }

    fun findGame(id: Long): BaseballGameResponse {
        val entity = baseballGameRepository.findById(id)
            .orElseThrow { NoSuchElementException("Game not found") }
        return BaseballGameResponse.of(game = entity.toDomain())
    }

    fun findAllGames(): BaseballGameResponses {
        val games = baseballGameRepository.findAll().map { it.toDomain() }
        return BaseballGameResponses.of(games = games)
    }


    fun deleteGame(id: Long) {
        baseballGameRepository.deleteById(id)
    }

    @Transactional
    fun updateGame(gameId: Long, status: GameStatus, updatedPlayers: List<PlayerDto>, newPlayerIdx: Int) {
        val entity = baseballGameRepository.findById(gameId)
            .orElseThrow { RuntimeException("해당 게임이 없습니다.") }

        entity.status = status
        entity.curPlayerIdx = newPlayerIdx

        val existingPlayers = entity.players.associateBy { it.id }
        updatedPlayers.forEach { dto ->
            val player = existingPlayers[dto.id]
            if (player != null) {
                player.isWinner = dto.isWinner
                player.history.clear()
                player.history.addAll(
                    dto.history.map {
                        HistoryEntity(
                            id = it.id,
                            input = it.input,
                            strike = it.strike,
                            ball = it.ball
                        )
                    }
                )
            } else {
                val newPlayer = PlayerEntity(
                    id = dto.id,
                    isWinner = dto.isWinner,
                    game = entity,
                    history = dto.history.map {
                        HistoryEntity(
                            id = it.id,
                            input = it.input,
                            strike = it.strike,
                            ball = it.ball
                        )
                    }.toMutableList()
                )
                entity.players.add(newPlayer)
            }
        }
        val playersToRemove = existingPlayers.keys - updatedPlayers.map { it.id }.toSet()
        entity.players.removeIf { it.id in playersToRemove }
    }


    private fun createRandomBaseballNumber(): BaseballNumber {
        val numbers = (1..9).shuffled().take(3)
        return BaseballNumber(numbers)
    }

    @Transactional
    fun tryBall(gameId: Long, request: BaseballGameTryBallRequest): BaseballGame {
        val entity = baseballGameRepository.findById(gameId)
            .orElseThrow { RuntimeException("해당 게임이 없습니다.") }

        val game = entity.toDomain()

        val isCurrentPlayer = game.players[game.curPlayerIdx].id == request.playerId
        if (!isCurrentPlayer) {
            throw RuntimeException("해당 플레이어 차례가 아닙니다.")
        }

        val numberList = request.input.map { it.toString().toInt() }
        val baseballNumber = BaseballNumber(numberList)

        val updatedGame = game.tryBall(baseballNumber)

        val updatedEntity = BaseballGameEntity.from(updatedGame)
        baseballGameRepository.save(updatedEntity)

        return updatedGame
    }

    @Transactional
    fun addPlayer(gameId: Long): BaseballGame {
        val entity = baseballGameRepository.findById(gameId)
            .orElseThrow { RuntimeException("해당 게임이 없습니다.") }

        val game = entity.toDomain()
        val updatedGame = game.addPlayer()

        val updatedEntity = BaseballGameEntity.from(domain = updatedGame)
        baseballGameRepository.save(updatedEntity)

        return updatedGame
    }

    @Transactional
    fun removePlayer(gameId: Long, playerId: Long): BaseballGame {
        val entity = baseballGameRepository.findById(gameId)
            .orElseThrow { RuntimeException("해당 게임이 없습니다.") }

        val game = entity.toDomain()
        val updatedGame = game.removePlayer(playerId = playerId)

        val updatedEntity = BaseballGameEntity.from(domain = updatedGame)
        baseballGameRepository.save(updatedEntity)

        return updatedGame
    }
}
