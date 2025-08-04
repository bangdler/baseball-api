package com.baseballgame.api.utils

object RoomCodeGenerator {
    fun generate(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..6)
            .map { chars.random() }
            .joinToString("")
    }
}
