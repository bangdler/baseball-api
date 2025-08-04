package com.baseballgame.api.fixture

import com.baseballgame.api.domain.Player
import com.baseballgame.api.entity.BaseballGameEntity
import com.baseballgame.api.entity.PlayerEntity

object PlayerFixture {
    fun player(gameEntity: BaseballGameEntity): PlayerEntity {
        val player = Player(
            isWinner = false,
            gameId = gameEntity.id,
        )
        return PlayerEntity.from(
            domain = player,
            game = gameEntity,
        )
    }
}