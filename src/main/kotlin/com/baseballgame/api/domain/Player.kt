package com.baseballgame.api.domain

class Player(
    val id: Long? = null,
    var isWinner: Boolean,
    val history: List<History> = emptyList(),
    val gameId: Long?,
) {
    fun tryBall(answer: BaseballNumber, baseballNumber: BaseballNumber): Player {
        val isWin = answer.isEqual(baseballNumber)
        val result = answer.compareTo(baseballNumber)

        val newHistory = history + History(
            id = (history.maxOfOrNull { it.id } ?: 0L) + 1L,
            input = baseballNumber.numbersToString(),
            strike = result.strike,
            ball = result.ball
        )

        return Player(id, isWinner = isWin, history = newHistory, gameId = gameId)
    }
}