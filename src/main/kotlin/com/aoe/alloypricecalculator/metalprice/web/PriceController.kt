package com.aoe.alloypricecalculator.metalprice.web

import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.metalprice.domain.CurrencyRateRepository
import com.aoe.alloypricecalculator.metalprice.domain.MetalPriceService
import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice
import org.springframework.http.MediaType
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

  @GetMapping("getListOfAllExchangeRates")
  fun getListOfAllExchangeRates() = currencyRateRepository.findAll()
}