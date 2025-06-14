package com.baseballgame.api.domain

import jakarta.persistence.*

@Entity
class Player(
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var isWinner: Boolean,

    @ElementCollection
    @CollectionTable(name = "player_history", joinColumns = [JoinColumn(name = "player_id")])
    val history: MutableList<History> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "game_id")
    val game: BaseballGame
)