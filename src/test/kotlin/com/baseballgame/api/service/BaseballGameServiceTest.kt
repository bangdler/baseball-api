package com.baseballgame.api.service

import com.baseballgame.api.domain.BaseballNumber
import com.baseballgame.api.dto.BaseballGameTryBallRequest
import com.baseballgame.api.entity.BaseballGameEntity
import com.baseballgame.api.entity.PlayerEntity
import com.baseballgame.api.fixture.SavedBaseballGameFixture
import com.baseballgame.api.fixture.SavedPlayerFixture
import com.baseballgame.api.test.BaseballGameSpringTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals


class BaseballGameServiceTest : BaseballGameSpringTest() {
    @Autowired
    private lateinit var sut: BaseballGameService


    @Test
    fun `플레이어 tryBall 요청 - 히스토리에 strike 0, ball 0이 추가된다`() {
        // given
        val 정답 = listOf(1, 2, 3)
        val 게임 = 게임_생성(정답, "테스트 게임")
        val 플레이어 = 플레이어_추가(게임)
        val request = BaseballGameTryBallRequest("456", 플레이어.id!!)

        // when
        val actual = sut.tryBall(
            게임.id!!,
            request = request,
        )

        // then
        assertEquals(actual.players[0].history.size, 1)
        assertEquals(actual.players[0].history[0].strike, 0)
        assertEquals(actual.players[0].history[0].ball, 0)
    }

    @Test
    fun `플레이어 tryBall 요청 - 히스토리에 strike 2, ball 0이 추가된다`() {
        // given
        val 정답 = listOf(1, 2, 3)
        val 게임 = 게임_생성(정답, "테스트 게임")
        val 플레이어 = 플레이어_추가(게임)
        val request = BaseballGameTryBallRequest("126", 플레이어.id!!)

        // when
        val actual = sut.tryBall(
            게임.id!!,
            request = request,
        )

        // then
        assertEquals(actual.players[0].history.size, 1)
        assertEquals(actual.players[0].history[0].strike, 2)
        assertEquals(actual.players[0].history[0].ball, 0)
    }

    @Test
    fun `플레이어 tryBall 요청 - 히스토리에 strike 1, ball 1이 추가된다`() {
        // given
        val 정답 = listOf(1, 2, 3)
        val 게임 = 게임_생성(정답, "테스트 게임")
        val 플레이어 = 플레이어_추가(게임)
        val request = BaseballGameTryBallRequest("137", 플레이어.id!!)

        // when
        val actual = sut.tryBall(
            게임.id!!,
            request = request,
        )

        // then
        assertEquals(actual.players[0].history.size, 1)
        assertEquals(actual.players[0].history[0].strike, 1)
        assertEquals(actual.players[0].history[0].ball, 1)
    }

    @Test
    fun `플레이어 tryBall 요청 - 히스토리에 strike 3, ball 0이 추가된다`() {
        // given
        val 정답 = listOf(1, 2, 3)
        val 게임 = 게임_생성(정답, "테스트 게임")
        val 플레이어 = 플레이어_추가(게임)
        val request = BaseballGameTryBallRequest("123", 플레이어.id!!)

        // when
        val actual = sut.tryBall(
            게임.id!!,
            request = request,
        )

        // then
        assertEquals(actual.players[0].history.size, 1)
        assertEquals(actual.players[0].history[0].strike, 3)
        assertEquals(actual.players[0].history[0].ball, 0)
    }

    @Test
    fun `플레이어 tryBall 요청 - 잘못된 input인 경우 에러`() {
        // given
        val 정답 = listOf(1, 2, 3)
        val 게임 = 게임_생성(정답, "테스트 게임")
        val 플레이어 = 플레이어_추가(게임)
        val request = BaseballGameTryBallRequest("113", 플레이어.id!!)

        // when then
        assertThrows<IllegalArgumentException> {
            sut.tryBall(게임.id!!, request)
        }.message.equals("BaseballNumber의 세 숫자는 모두 달라야 합니다.")
    }


    private fun 게임_생성(answer: List<Int>, name: String): BaseballGameEntity {
        return SavedBaseballGameFixture.init(
            baseballGameRepository = baseballGameRepository,
            answer = BaseballNumber(numbers = answer),
            name = name
        )
    }

    private fun 플레이어_추가(gameEntity: BaseballGameEntity): PlayerEntity {
        return SavedPlayerFixture.init(
            playerRepository = playerRepository,
            baseballGameEntity = gameEntity
        )
    }
}