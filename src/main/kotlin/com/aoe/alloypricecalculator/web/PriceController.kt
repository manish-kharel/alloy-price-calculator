package com.aoe.alloypricecalculator.web

import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.MeasurementService
import com.aoe.alloypricecalculator.domain.MetalPriceService
import com.aoe.alloypricecalculator.domain.model.Authentication
import com.aoe.alloypricecalculator.domain.model.GrpcUser
import com.aoe.alloypricecalculator.exception.InvalidAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class PriceController(
  private val metalPriceService: MetalPriceService,
  private val measurementService: MeasurementService
) {
  private val logger = createSlf4jLogger()

  @GetMapping(path = ["/prices"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getMetalPrices() = metalPriceService.getMetalPrices().also {
    logger.info("working")
  }

  @PostMapping(path = ["/getAuthentication"], consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun getAuthentication(
    @RequestBody grpcUser: GrpcUser
  ): ResponseEntity<Authentication> = measurementService.getAuthentication(grpcUser).let { authentication ->
    if (authentication.token.isNotBlank())
      ResponseEntity(authentication, HttpStatus.OK)
    else
      throw InvalidAuthenticationException(authentication, "Username and Password did not match")
  }

  @GetMapping(path = ["/getAnalysisSummaryList"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun requestAnalysisSummaryList(
    @RequestHeader(required = true) authentication: Authentication
  ) = measurementService.getMeasurementList(authentication)

  @GetMapping(path = ["/getMeasurement"])
  fun requestMeasurementValues(
    @RequestHeader(required = true) authentication: Authentication,
    @RequestParam uuid: String
  ) = measurementService.getMeasurementValues(authentication, uuid)
}