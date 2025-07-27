package com.baseballgame.api.entity

import HistoryEntity
import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.Player
import jakarta.persistence.*

@Entity
@Table(name = "player")
class PlayerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var isWinner: Boolean,

    @ElementCollection
    @CollectionTable(name = "player_history", joinColumns = [JoinColumn(name = "player_id")])
    val history: MutableList<HistoryEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    val game: BaseballGameEntity
) {
    fun toDomain(): Player {
        return Player(
            id = id,
            isWinner = isWinner,
            history = history.map { it.toDomain() }.toList(),
            gameId = game.id
        )
    }

    companion object {
        fun from(domain: Player, game: BaseballGame): PlayerEntity {
            return PlayerEntity(
                id = domain.id,
                isWinner = domain.isWinner,
                history = domain.history.map { HistoryEntity.from(it) }.toMutableList(),
                game = BaseballGameEntity.from(game)
            )
        }
    }
}