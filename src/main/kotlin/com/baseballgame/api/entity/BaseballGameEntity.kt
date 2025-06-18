package com.baseballgame.api.entity

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.GameStatus
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "baseball_game")
class BaseballGameEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    @Enumerated(EnumType.STRING)
    var status: GameStatus = GameStatus.IDLE,

    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val players: MutableList<PlayerEntity> = mutableListOf(),

    var curPlayerIdx: Int = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "answer_id")
    val answer: BaseballNumberEntity
) {
    fun toDomain(): BaseballGame {
        return BaseballGame(
            id = id,
            name = name,
            status = status,
            players = players.map { it.toDomain() },
            curPlayerIdx = curPlayerIdx,
            answer = answer.toDomain()
        )
    }

    companion object {
        fun from(domain: BaseballGame): BaseballGameEntity {
            return BaseballGameEntity(
                id = domain.id,
                name = domain.name,
                status = domain.status,
                players = domain.players.map { PlayerEntity.from(it) }.toMutableList(),
                curPlayerIdx = domain.curPlayerIdx,
                answer = BaseballNumberEntity.from(domain.answer)
            )
        }
    }
}