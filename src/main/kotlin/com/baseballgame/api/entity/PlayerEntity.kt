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

    @ManyToOne
    @JoinColumn(name = "game_id")
    val game: BaseballGameEntity
) {
    fun toDomain(): Player =
        Player(
            id = id,
            isWinner = isWinner,
            history = history.map { it.toDomain() }.toList(),
            game = game.toDomain()
        )

    companion object {
        fun from(domain: Player): PlayerEntity =
            PlayerEntity(
                id = domain.id,
                isWinner = domain.isWinner,
                history = domain.history.map { HistoryEntity.from(it) }.toMutableList(),
                game = BaseballGameEntity.from(domain.game)
            )
    }
}