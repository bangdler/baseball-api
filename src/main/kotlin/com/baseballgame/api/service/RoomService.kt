package com.baseballgame.api.service

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.Player
import com.baseballgame.api.entity.BaseballGameEntity
import com.baseballgame.api.entity.RoomEntity
import com.baseballgame.api.repository.BaseballGameRepository
import com.baseballgame.api.repository.RoomRepository
import com.baseballgame.api.utils.RandomBaseballNumberGenerator
import com.baseballgame.api.utils.RoomCodeGenerator
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val gameRepository: BaseballGameRepository,
    private val baseballGameService: BaseballGameService
) {
    fun createRoom(name: String): RoomEntity {
        val answer = RandomBaseballNumberGenerator.createRandomBaseballNumber()
        val game = BaseballGame(name = name, answer = answer)

        val gameEntity = BaseballGameEntity.from(domain = game)
        val roomEntity = RoomEntity(roomCode = RoomCodeGenerator.generate(), game = gameEntity)
        return roomEntity
    }

    fun joinRoom(roomCode: String): Player {
        val room = roomRepository.findByRoomCode(roomCode)
            ?: throw IllegalArgumentException("방이 존재하지 않습니다.")
        val gameEntity = room.game ?: throw IllegalStateException("게임이 초기화되지 않았습니다.")
        if (gameEntity.players.size >= 2) throw IllegalStateException("방 정원이 가득 찼습니다.")
        val game = gameEntity.toDomain();
        val updatedGame = game.addPlayer();
        val updatedGameEntity = BaseballGameEntity.from(updatedGame);
        gameRepository.save(updatedGameEntity);
        return updatedGame.players.last()
    }


}