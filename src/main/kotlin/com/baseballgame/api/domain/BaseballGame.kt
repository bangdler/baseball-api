package com.baseballgame.api.domain

class BaseballGame(
    val id: Long? = null,
    val name: String,
    var status: GameStatus = GameStatus.IDLE,
    val players: List<Player> = emptyList(),
    var curPlayerIdx: Int = 0,
    val answer: BaseballNumber
) {
    fun addPlayer(): BaseballGame {
        val newPlayer = Player(isWinner = false, game = this)

        val newPlayers = players.plus(newPlayer)

        return BaseballGame(id, name, status, newPlayers, curPlayerIdx, answer)
    }

    fun removePlayer(id: Long): BaseballGame {
        val newPlayers = players.filter { it.id != id }

        return BaseballGame(id, name, status, newPlayers, curPlayerIdx, answer)
    }

    fun tryBall(baseballNumber: BaseballNumber): BaseballGame {
        if (players.isEmpty()) {
            throw IllegalStateException("플레이어가 존재하지 않습니다.")
        }
        
        val currentPlayer = players[curPlayerIdx];
        val updatedPlayer = currentPlayer.tryBall(answer, baseballNumber)

        val newPlayers = players.toMutableList().apply { this[curPlayerIdx] = updatedPlayer }.toList()

        val newStatus = if (updatedPlayer.isWinner) GameStatus.END else GameStatus.PROGRESS

        val newCurPlayerIdx = turnNext(curPlayerIdx, newPlayers)

        return BaseballGame(id, name, status = newStatus, players = newPlayers, curPlayerIdx = newCurPlayerIdx, answer)
    }

    fun turnNext(idx: Int, players: List<Player>): Int =
        if (idx + 1 > players.lastIndex) 0 else idx + 1
}