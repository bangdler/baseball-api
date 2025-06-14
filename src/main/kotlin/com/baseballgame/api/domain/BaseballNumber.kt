package com.baseballgame.api.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class BaseballNumber(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val number1: Int,
    val number2: Int,
    val number3: Int
) {
    init {
        require(number1 != number2 && number2 != number3 && number1 != number3) {
            "BaseballNumber의 세 숫자는 모두 달라야 합니다."
        }
    }
}
