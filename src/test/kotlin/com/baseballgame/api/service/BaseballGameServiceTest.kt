package com.baseballgame.api.service

import com.baseballgame.api.domain.GameStatus
import com.baseballgame.api.domain.History
import com.baseballgame.api.dto.PlayerResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class BaseballGameServiceTest(
    @Autowired private val baseballGameService: BaseballGameService
) {
    @Test
//    @Rollback(false)
    fun createGame() {
        val gameName = "테스트 게임"
        val createdGame = baseballGameService.createGame(gameName)
        assertNotNull(createdGame.id)
        assertEquals(gameName, createdGame.name)

        val foundGame = createdGame.id?.let { baseballGameService.findGame(it) }
        assertEquals(createdGame.id, foundGame?.id)
    }

    @Test
    fun deleteGame() {
        val gameName = "삭제 테스트 게임"
        val createdGame = baseballGameService.createGame(gameName)
        assertNotNull(createdGame.id)

        baseballGameService.deleteGame(createdGame.id!!)

        assertThrows(NoSuchElementException::class.java) {
            baseballGameService.findGame(createdGame.id!!)
        }
    }

    @Test
    fun updateGame() {
        val gameName = "업데이트 테스트 게임"
        val createdGame = baseballGameService.createGame(gameName)
        assertNotNull(createdGame.id)

        val updatedPlayers = listOf(
            PlayerResponse(
                id = 1,
                isWinner = true,
                history = listOf(
                    History(id = 123, input = "123", strike = 2, ball = 1)
                )
            )
        )

        baseballGameService.updateGame(createdGame.id!!, GameStatus.PROGRESS, updatedPlayers, 1)

        val updatedGame = baseballGameService.findGame(createdGame.id!!)
        assertEquals(1, updatedGame.players.size)
        assertEquals(true, updatedGame.players.first().isWinner)
        assertEquals("123", updatedGame.players.first().history.first().input)
        assertEquals(2, updatedGame.players.first().history.first().strike)
        assertEquals(1, updatedGame.players.first().history.first().ball)
    }

}