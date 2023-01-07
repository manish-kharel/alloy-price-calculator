package com.aoe.alloypricecalculator.service

import com.aoe.alloypricecalculator.service.converter.MetalPriceConverter
import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.MetalPriceRepository
import com.aoe.alloypricecalculator.domain.MetalPriceService
import com.aoe.alloypricecalculator.domain.model.Metal
import com.aoe.alloypricecalculator.domain.model.MetalPrice
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.util.Streamable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Service
class MetalPriceServiceImpl(
  @Qualifier("mockApiRestTemplate")
  private val restTemplate: RestTemplate,
  private val metalPriceConverter: MetalPriceConverter,
  private val metalPriceRepository: MetalPriceRepository
) : MetalPriceService {

  private val logger = createSlf4jLogger()

  @Cacheable(
    cacheNames = ["PriceRepository.find"],
    unless = "#result == null",
  )
  override fun getMetalPrices(): List<MetalPrice>? = try {
    restTemplate
      .getForObject("/prices2", String::class.java)
      ?.let { response ->
        metalPriceConverter.convert(response).also {
          if (it.isNotEmpty()) {
            saveAllOrUpdateAll(it)
          }
        }
        metalPriceRepository.findAll()
      }
  } catch (ex: RestClientException) {
    logger.error("External API not responding", ex)
    metalPriceRepository.findAll()
  }

  private fun saveAllOrUpdateAll(prices: List<MetalPrice>) {
    if (metalPriceRepository.findAll() == emptyList<MetalPrice>()) {
      metalPriceRepository.saveAll(prices)
    } else {
      prices.forEach { price ->
        metalPriceRepository.update(
          price.costPerUnit,
          price.baseCurrency,
          price.lastUpdated,
          price.unit,
          price.name
        )
      }
    }
  }
}
