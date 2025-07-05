package com.baseballgame.api.entity

import com.baseballgame.api.domain.BaseballNumber
import jakarta.persistence.*

@Entity
@Table(name = "baseball_number")
class BaseballNumberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val number1: Int,
    val number2: Int,
    val number3: Int,
) {
    fun toDomain(): BaseballNumber {
        return BaseballNumber(listOf(number1, number2, number3))
    }

    companion object {
        fun from(domain: BaseballNumber): BaseballNumberEntity {
            val numbers = domain.toList();
            return BaseballNumberEntity(
                number1 = numbers[0],
                number2 = numbers[1],
                number3 = numbers[2]
            )
        }
    }
}