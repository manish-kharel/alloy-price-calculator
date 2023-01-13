package com.aoe.alloypricecalculator.infrastructure

import com.aoe.alloypricecalculator.domain.MetalPriceClient
import com.aoe.alloypricecalculator.domain.model.MetalPriceClientResponse
import com.aoe.alloypricecalculator.domain.exception.MetalPriceClientException
import com.aoe.alloypricecalculator.infrastructure.converter.MetalPriceConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class MetalPriceClientImpl(
  @Qualifier("mockApiRestTemplate")
  private val restTemplate: RestTemplate,
  private val metalPriceConverter: MetalPriceConverter,
) : MetalPriceClient {

  override fun getMetalPrices() = try {
    restTemplate
      .getForObject("/prices2", String::class.java)!!.let { response ->
        MetalPriceClientResponse(
          metalPriceConverter.convertMetalPrices(response),
          metalPriceConverter.convertCurrencyRates(response),
        )
      }
  } catch (ex: RestClientException) {
    throw MetalPriceClientException("Rest client failed to get data from Metals API")
  }
}