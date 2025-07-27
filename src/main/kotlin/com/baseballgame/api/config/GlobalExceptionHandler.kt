package com.baseballgame.api.config

import com.baseballgame.api.exception.ErrorMessage
import com.baseballgame.api.exception.ErrorResponse
import com.baseballgame.api.exception.GameNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalException(e: Exception): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(ErrorMessage(e.message.orEmpty()))
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFound(e: GameNotFoundException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(ErrorMessage(e.message.orEmpty()))
        return ResponseEntity.badRequest().body(body)
    }
}