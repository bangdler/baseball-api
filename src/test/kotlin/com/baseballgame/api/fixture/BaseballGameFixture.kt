package com.baseballgame.api.fixture

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.BaseballNumber
import com.baseballgame.api.entity.BaseballGameEntity

object BaseballGameFixture {
    fun baseballGame(answer: BaseballNumber, name: String): BaseballGameEntity {
        val baseballGame = BaseballGame(
            name = name,
            answer = answer
        )

        return BaseballGameEntity.from(baseballGame)
    }
}