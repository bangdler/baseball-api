package com.baseballgame.api.utils

import com.baseballgame.api.domain.BaseballNumber

object RandomBaseballNumberGenerator {
    fun createRandomBaseballNumber(): BaseballNumber {
        val numbers = (1..9).shuffled().take(3)
        return BaseballNumber(numbers)
    }
}