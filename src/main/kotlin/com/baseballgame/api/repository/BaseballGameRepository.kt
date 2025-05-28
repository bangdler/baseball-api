package com.baseballgame.api.repository

import com.baseballgame.api.domain.BaseballGame
import org.springframework.data.jpa.repository.JpaRepository

interface BaseballGameRepository : JpaRepository<BaseballGame, Long>
