package com.aoe.alloypricecalculator.web

import com.aoe.alloypricecalculator.domain.MetalPriceService
import com.aoe.alloypricecalculator.domain.MeasurementService
import com.aoe.alloypricecalculator.domain.model.Authentication
import com.aoe.alloypricecalculator.domain.model.Measurement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PriceController(
  private val metalPriceService: MetalPriceService,
  private val measurementService: MeasurementService
) {

  @GetMapping(path = ["/prices"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getMetalPrices() = metalPriceService.getMetalPrices()

  @GetMapping(path = ["/test"])
  fun getAuthentication() = measurementService.checkAuthentication()


  @GetMapping(path = ["/test2"])
  fun requestAnalysisSummaryList(
//    @RequestBody(required = true) authentication: Authentication
  ) = Authentication(
    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
        ".eyJleHAiOjE3MDE3OTQyMjgsInNpZCI6IjdjYWEwNzY2LTZiNjktNDc3Ni05MmEwLTZjM2JhYjBkMmIwMiIsInVzZXJuYW1lIjoicGluIn0." +
        "SH0Newv0-sFIBi9IV85eRzDh7t7gG77SW0_GxTKEHv8",
    isValid = true
  ).let {
  measurementService.getMeasurementList(it)
  }


  @GetMapping(path = ["/test3"])
  fun requestMeasurementValues() =
    Authentication(
      token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzA1OTM4NTQsInNpZCI6ImI4MjYzOGJiLWY0YjAtNDljYS04YjNjLT" +
          "NjMTk3ZWVhMWEyMyIsInVzZXJuYW1lIjoicGluIn0.j4KJSv5s60_15impajsjOSNZXSEtMcvC1JSIqMTzKCA",
      isValid = true
    ).let {
   measurementService.getMeasurementValues(it, "de13b4e3-aa8c-4379-bacd-d39ab037655e")
    }
}