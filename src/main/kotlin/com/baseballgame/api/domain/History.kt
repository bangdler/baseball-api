package com.baseballgame.api.domain

import jakarta.persistence.Embeddable

@Embeddable
data class History(
    val id: Long,
    val input: String,
    val strike: Int,
    val ball: Int
)
