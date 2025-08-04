package com.baseballgame.api.exception

data class ErrorResponse(
    val error: ErrorMessage
)

data class ErrorMessage(val message: String)