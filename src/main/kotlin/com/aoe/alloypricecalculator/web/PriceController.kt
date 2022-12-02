package com.aoe.alloypricecalculator.web

import com.aoe.alloypricecalculator.service.GrpcService
import com.aoe.alloypricecalculator.service.PriceService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PriceController(
  private val priceRepository: PriceService,
  private val grpcService: GrpcService
) {

 @GetMapping(path = ["/prices" ], produces = [MediaType.APPLICATION_JSON_VALUE])
 fun getMetalPrices() = priceRepository.getMetalPrices()

  @GetMapping(path = ["/test"])
  fun getAuthentication() = grpcService.getAuthentication()
}