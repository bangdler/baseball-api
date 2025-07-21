package com.baseballgame.api.repository

import com.baseballgame.api.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<RoomEntity, Long> {
    fun findByRoomCode(roomCode: String): RoomEntity?
}
