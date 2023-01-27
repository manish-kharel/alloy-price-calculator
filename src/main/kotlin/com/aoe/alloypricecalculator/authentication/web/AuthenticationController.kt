package com.aoe.alloypricecalculator.authentication.web

import com.aoe.alloypricecalculator.authentication.domain.AuthenticationService
import com.aoe.alloypricecalculator.authentication.domain.model.Authentication
import com.aoe.alloypricecalculator.authentication.domain.model.GrpcUser
import com.aoe.alloypricecalculator.exception.InvalidAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class AuthenticationController(
  private val authenticationService: AuthenticationService
) {

  @PostMapping(path = ["/getAuthentication"], consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun getAuthentication(
    @RequestBody grpcUser: GrpcUser
  ): ResponseEntity<Authentication> = authenticationService.getAuthentication(grpcUser).let { authentication ->
    if (authentication.token.isNotBlank())
      ResponseEntity(authentication, HttpStatus.OK)
    else
      throw InvalidAuthenticationException(authentication, "Username and Password did not match")
  }
}