package com.baseballgame.api.entity

import jakarta.persistence.*

enum class RoomStatus {
    WAITING, PLAYING, FINISHED
}

@Entity
@Table(name = "room")
class RoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    val status: RoomStatus = RoomStatus.WAITING,

    @Column(unique = true)
    val roomCode: String,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "game_id")
    var game: BaseballGameEntity? = null
)