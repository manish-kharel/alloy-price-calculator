package com.aoe.alloypricecalculator.exception

import com.aoe.alloypricecalculator.domain.model.Authentication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

  @org.springframework.web.bind.annotation.ExceptionHandler(value = [(InvalidAuthenticationException::class)])
  fun handleInvalidAuthenticationException(ex: InvalidAuthenticationException): ResponseEntity<Authentication> {
    return ResponseEntity(ex.authentication, HttpStatus.UNAUTHORIZED)
  }
}