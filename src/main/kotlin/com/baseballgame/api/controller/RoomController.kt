package com.baseballgame.api.controller

import com.baseballgame.api.dto.PlayerDto
import com.baseballgame.api.service.RoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/rooms"])
class RoomController(
    private val roomService: RoomService
) {

    data class CreateRoomRequest(val name: String)

    @PostMapping
    fun createRoom(@RequestBody request: CreateRoomRequest): ResponseEntity<String> {
        val room = roomService.createRoom(request.name)
        return ResponseEntity.status(HttpStatus.CREATED).body(room.roomCode)
    }

    @PostMapping("/{roomCode}/join")
    fun joinRoom(@PathVariable roomCode: String): ResponseEntity<PlayerDto> {
        val player = roomService.joinRoom(roomCode)
        return ResponseEntity.status(HttpStatus.CREATED).body(PlayerDto.from(player))
    }

}