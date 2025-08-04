package com.baseballgame.api.dto

import com.baseballgame.api.domain.BaseballGame

data class BaseballGameResponses(val baseballGameResponses: List<BaseballGameResponse>) {
    companion object {
        fun of(games: List<BaseballGame>): BaseballGameResponses {
            return BaseballGameResponses(
                games.map { BaseballGameResponse.of(it) }
            )
        }
    }
}
