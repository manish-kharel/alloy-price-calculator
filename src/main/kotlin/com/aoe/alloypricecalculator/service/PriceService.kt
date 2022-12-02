package com.aoe.alloypricecalculator.service

import client.v1.AuthEntity
import client.v1.AuthenticationServiceGrpc
import com.aoe.alloypricecalculator.converter.PriceConverter
import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.PriceRepository
import com.aoe.alloypricecalculator.domain.model.Price
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class PriceService(
  @Qualifier("mockApiRestTemplate")
  private val restTemplate: RestTemplate,
  private val priceConverter: PriceConverter,
  private val priceRepository: PriceRepository
) {

  private val logger = createSlf4jLogger()

  @Cacheable(
    cacheNames = ["PriceRepository.find"],
    unless = "#result == null",
  )
  fun getMetalPrices(): Price? = try {
    restTemplate
      .getForObject("/prices2", String::class.java)
      ?.let { response ->
        priceConverter.convert(response)
          .also {
          price -> priceRepository.save(price)
        }
      }
  } catch (ex: RestClientException) {
    logger.error("External API not responding", ex)
    null
  }
}
