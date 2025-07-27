package com.baseballgame.api.controller

import com.baseballgame.api.domain.GameStatus
import com.baseballgame.api.dto.*
import com.baseballgame.api.service.BaseballGameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/games")
class BaseballGameController(
    private val baseballGameService: BaseballGameService
) {
    @PostMapping
    fun createGame(@RequestBody request: BaseballGameCreateRequest): ResponseEntity<Long> {
        val gameResponse = baseballGameService.createGame(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse.id)
    }

    @GetMapping("/list")
    fun getAllGames(): ResponseEntity<BaseballGameResponses> {
        return ResponseEntity.ok(baseballGameService.findAllGames())
    }

    @GetMapping("/{id:[0-9]+}")
    fun getGame(@PathVariable id: Long): ResponseEntity<BaseballGameResponse> =
        ResponseEntity.ok(baseballGameService.findGame(id))

    @DeleteMapping("/{id:[0-9]+}")
    fun deleteGame(@PathVariable id: Long): ResponseEntity<Void> {
        baseballGameService.deleteGame(id)
        return ResponseEntity.noContent().build()
    }

    data class UpdateGameRequest(
        val status: GameStatus,
        val updatedPlayers: List<PlayerDto>,
        val curPlayerIdx: Int
    )

    @PutMapping("/{id:[0-9]+}")
    fun updateGame(
        @PathVariable id: Long,
        @RequestBody request: UpdateGameRequest
    ): ResponseEntity<Void> {
        baseballGameService.updateGame(id, request.status, request.updatedPlayers, request.curPlayerIdx)
        return ResponseEntity.noContent().build()
    }


    @PostMapping("/{gameId}/try-ball")
    fun tryBall(
        @PathVariable gameId: Long,
        @RequestBody request: BaseballGameTryBallRequest
    ): ResponseEntity<BaseballGameResponse> {
        val updatedGame = baseballGameService.tryBall(gameId, request)
        return ResponseEntity.ok(BaseballGameResponse.of(updatedGame))
    }

}

