package com.baseballgame.api.fixture

import com.baseballgame.api.entity.BaseballGameEntity
import com.baseballgame.api.entity.PlayerEntity
import com.baseballgame.api.repository.PlayerRepository

object SavedPlayerFixture {
    fun init(playerRepository: PlayerRepository, baseballGameEntity: BaseballGameEntity): PlayerEntity {
        val playerEntity = PlayerFixture.player(gameEntity = baseballGameEntity)
        baseballGameEntity.addPlayer(playerEntity)
        return playerRepository.save(playerEntity)
    }
}