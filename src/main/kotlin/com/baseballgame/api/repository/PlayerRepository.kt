package com.baseballgame.api.repository

import com.baseballgame.api.entity.PlayerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerRepository : JpaRepository<PlayerEntity, Long> {
}