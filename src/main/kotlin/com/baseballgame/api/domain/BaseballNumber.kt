package com.baseballgame.api.domain

class BaseballNumber(
    private val numbers: List<Int>
) {
    companion object {
        private const val REQUIRED_SIZE = 3
    }

    init {
        validate(numbers)
    }

    private fun validate(numbers: List<Int>) {
        require(numbers.size == REQUIRED_SIZE && numbers.distinct().size == REQUIRED_SIZE) {
            "BaseballNumber의 세 숫자는 모두 달라야 합니다."
        }
    }

    fun toList(): List<Int> = numbers.toList()
}
