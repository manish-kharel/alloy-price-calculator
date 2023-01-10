package com.aoe.alloypricecalculator.service

import com.aoe.alloypricecalculator.service.converter.MetalPriceConverter
import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.CurrencyRateRepository
import com.aoe.alloypricecalculator.domain.MetalPriceRepository
import com.aoe.alloypricecalculator.domain.MetalPriceService
import com.aoe.alloypricecalculator.domain.model.Currency
import com.aoe.alloypricecalculator.domain.model.MetalPrice
import com.aoe.alloypricecalculator.exception.MetalsApiFailedException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class MetalPriceServiceImpl(
  @Qualifier("mockApiRestTemplate")
  private val restTemplate: RestTemplate,
  private val metalPriceConverter: MetalPriceConverter,
  private val metalPriceRepository: MetalPriceRepository,
  private val currencyRateRepository: CurrencyRateRepository,
) : MetalPriceService {

  private val logger = createSlf4jLogger()

  override fun getMetalPrices(): List<MetalPrice> = try {
    restTemplate
      .getForObject("/prices2", String::class.java)!!.let { response ->
        metalPriceConverter.convertCurrencyRates(response).also {
          if (it.isNotEmpty()) {
            saveCurrencyRatesOrUpdateExisting(it)
          }
        }
        metalPriceConverter.convertMetalPrices(response).also {
          if (it.isNotEmpty()) {
            savePricesOrUpdateExisting(it)
          }
        }
        metalPriceRepository.findAll()
      }
  } catch (ex: RestClientException) {
    logger.error("External API not responding")
    val savedPrices = metalPriceRepository.findAll()
    throw MetalsApiFailedException(savedPrices, "External API not responding")
  }

  private fun saveCurrencyRatesOrUpdateExisting(currencies: List<Currency>) {
    if (currencyRateRepository.findAll() == emptyList<MetalPrice>()) {
      currencyRateRepository.saveAllAndFlush(currencies)

    } else {
      currencies.forEach { currency ->
        currencyRateRepository.update(
          currency.currency,
          currency.rate
        )
      }
    }
  }

  private fun savePricesOrUpdateExisting(prices: List<MetalPrice>) {
    if (metalPriceRepository.findAll() == emptyList<MetalPrice>()) {
      metalPriceRepository.saveAllAndFlush(prices)
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
