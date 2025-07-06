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

    fun numbersToString(): String = numbers.joinToString("")

    fun compareTo(other: BaseballNumber): Result {
        var strike = 0;
        var ball = 0;

        val otherNumbers = other.toList();
        for (i in numbers.indices) {
            val cur = numbers[i];
            if (cur == otherNumbers[i]) {
                strike++;
            } else if (otherNumbers.contains(cur)) {
                ball++;
            }
        }

        return Result(strike, ball)
    }

    fun isEqual(other: BaseballNumber): Boolean {
        return numbers == other.numbers
    }

    data class Result(val strike: Int, val ball: Int)
}
