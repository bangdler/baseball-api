package com.baseballgame.api.entity

import HistoryEntity
import com.baseballgame.api.domain.Player
import jakarta.persistence.*

@Entity
@Table(name = "player")
class PlayerEntity(
    @Id
    val id: Long,

    var isWinner: Boolean,

    @ElementCollection
    @CollectionTable(name = "player_history", joinColumns = [JoinColumn(name = "player_id")])
    val history: MutableList<HistoryEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    val game: BaseballGameEntity
) {
    fun toDomain(skipGame: Boolean = false): Player {
        return Player(
            id = id,
            isWinner = isWinner,
            history = history.map { it.toDomain() }.toList(),
            game = if (skipGame) null else game.toDomain(skipPlayers = true)
        )
    }

    companion object {
        fun from(domain: Player): PlayerEntity {
            val gameEntity = domain.game?.let { BaseballGameEntity.from(it) }
                ?: throw IllegalArgumentException("Player의 game은 null일 수 없습니다.")

            return PlayerEntity(
                id = domain.id,
                isWinner = domain.isWinner,
                history = domain.history.map { HistoryEntity.from(it) }.toMutableList(),
                game = gameEntity
            )
        }
    }
}