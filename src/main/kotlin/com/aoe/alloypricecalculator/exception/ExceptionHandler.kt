package com.aoe.alloypricecalculator.exception

import com.aoe.alloypricecalculator.authentication.domain.model.Authentication
import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(value = [(InvalidAuthenticationException::class)])
  fun handleInvalidAuthenticationException(ex: InvalidAuthenticationException): ResponseEntity<Authentication> {
    return ResponseEntity(ex.authentication, HttpStatus.UNAUTHORIZED)
  }

  @ExceptionHandler(value = [(ExpiredTokenException::class)])
  fun handleExpiredTokenException(ex: ExpiredTokenException): ResponseEntity<Authentication> {
    return ResponseEntity(Authentication(""), HttpStatus.UNAUTHORIZED)
  }

  @ExceptionHandler(value = [(MetalsApiFailedException::class)])
  fun handleMetalsApiFailedException(ex: MetalsApiFailedException): ResponseEntity<List<MetalPrice>> {
//    return ResponseEntity(ex.prices, HttpStatus.valueOf(209))
    return ResponseEntity(ex.prices, HttpStatus.NON_AUTHORITATIVE_INFORMATION)
  }
}