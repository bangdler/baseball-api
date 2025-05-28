package com.baseballgame.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class BaseballGame(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    var isEnd: Boolean = false,
    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val players: MutableList<Player> = mutableListOf()
)