package com.aoe.alloypricecalculator.web

import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.CurrencyRateRepository
import com.aoe.alloypricecalculator.domain.MetalPriceService
import com.aoe.alloypricecalculator.domain.model.MetalPrice
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class PriceController(
  private val metalPriceService: MetalPriceService,
  private val currencyRateRepository: CurrencyRateRepository,
) {
  private val logger = createSlf4jLogger()

  @GetMapping(path = ["/prices"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getMetalPrices(): List<MetalPrice> = metalPriceService.getMetalPrices()
//  metalPriceService.getMetalPrices()

  @GetMapping("getListOfAllExchangeRates")
  fun getListOfAllExchangeRates() = currencyRateRepository.findAll()
}