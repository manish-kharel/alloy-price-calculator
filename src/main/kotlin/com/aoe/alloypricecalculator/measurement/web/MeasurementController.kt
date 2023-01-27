package com.aoe.alloypricecalculator.measurement.web

import com.aoe.alloypricecalculator.measurement.domain.MeasurementService
import com.aoe.alloypricecalculator.authentication.domain.model.Authentication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class MeasurementController(
  private val measurementService: MeasurementService
) {

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