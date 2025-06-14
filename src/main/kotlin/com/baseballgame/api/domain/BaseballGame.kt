package com.baseballgame.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class BaseballGame(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    @Enumerated(EnumType.STRING)
    var status: GameStatus = GameStatus.IDLE,
    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val players: MutableList<Player> = mutableListOf(),
    var curPlayerIdx: Int = 0,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "answer_id")
    val answer: BaseballNumber
)