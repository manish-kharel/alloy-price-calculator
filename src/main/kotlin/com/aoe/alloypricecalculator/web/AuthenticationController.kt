package com.aoe.alloypricecalculator.web

import com.aoe.alloypricecalculator.domain.MeasurementService
import com.aoe.alloypricecalculator.domain.model.Authentication
import com.aoe.alloypricecalculator.domain.model.GrpcUser
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
  private val measurementService: MeasurementService
) {

  @PostMapping(path = ["/getAuthentication"], consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun getAuthentication(
    @RequestBody grpcUser: GrpcUser
  ): ResponseEntity<Authentication> = measurementService.getAuthentication(grpcUser).let { authentication ->
    if (authentication.token.isNotBlank())
      ResponseEntity(authentication, HttpStatus.OK)
    else
      throw InvalidAuthenticationException(authentication, "Username and Password did not match")
  }
}