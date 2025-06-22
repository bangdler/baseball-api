package com.baseballgame.api.repository

import com.baseballgame.api.entity.BaseballGameEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BaseballGameRepository : JpaRepository<BaseballGameEntity, Long>
