package com.baseballgame.api.controller

import com.baseballgame.api.domain.BaseballGame
import com.baseballgame.api.domain.GameStatus
import com.baseballgame.api.dto.BaseballGameDto
import com.baseballgame.api.dto.PlayerDto
import com.baseballgame.api.service.BaseballGameService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/games")
class BaseballGameController(
    private val baseballGameService: BaseballGameService
) {

    data class CreateGameRequest(val name: String)

    @PostMapping
    fun createGame(@RequestBody request: CreateGameRequest): ResponseEntity<BaseballGame> {
        val game = baseballGameService.createGame(request.name)
        return ResponseEntity.status(HttpStatus.CREATED).body(game)
    }

    @GetMapping("/list")
    fun getAllGames(): List<BaseballGame> {
        return baseballGameService.findAllGames()
    }

    @GetMapping("/{id:[0-9]+}")
    fun getGame(@PathVariable id: Long): BaseballGameDto =
        baseballGameService.findGame(id)

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
        return ResponseEntity.noContent().build()  // 204 No Content 응답
    }
}

