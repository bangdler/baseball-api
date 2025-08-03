package com.baseballgame.api.fixture

import com.baseballgame.api.domain.BaseballNumber
import com.baseballgame.api.entity.BaseballGameEntity
import com.baseballgame.api.repository.BaseballGameRepository

object SavedBaseballGameFixture {
    fun init(baseballGameRepository: BaseballGameRepository, answer: BaseballNumber, name: String): BaseballGameEntity {
        val baseballGameEntity = BaseballGameFixture.baseballGame(answer = answer, name = name)
        return baseballGameRepository.save(baseballGameEntity)
    }
}