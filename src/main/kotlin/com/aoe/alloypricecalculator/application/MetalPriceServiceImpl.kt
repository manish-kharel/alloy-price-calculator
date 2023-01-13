package com.aoe.alloypricecalculator.application

import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.CurrencyRateRepository
import com.aoe.alloypricecalculator.domain.MetalPriceClient
import com.aoe.alloypricecalculator.domain.MetalPriceRepository
import com.aoe.alloypricecalculator.domain.MetalPriceService
import com.aoe.alloypricecalculator.domain.model.Currency
import com.aoe.alloypricecalculator.domain.model.MetalPrice
import com.aoe.alloypricecalculator.domain.exception.MetalPriceClientException
import com.aoe.alloypricecalculator.domain.exception.MetalsApiFailedException
import org.springframework.stereotype.Service

@Service
class MetalPriceServiceImpl(
  private val metalPriceClient: MetalPriceClient,
  private val metalPriceRepository: MetalPriceRepository,
  private val currencyRateRepository: CurrencyRateRepository,
) : MetalPriceService {

  private val logger = createSlf4jLogger()

  override fun getMetalPrices(): List<MetalPrice> = try {
    metalPriceClient.getMetalPrices().let {
      if (it.metalprices.isNotEmpty()) {
        savePricesOrUpdateExisting(it.metalprices)
      }

      if (it.currencyRates.isNotEmpty()) {
        saveCurrencyRatesOrUpdateExisting(it.currencyRates)
      }
    }
    metalPriceRepository.findAll()

  } catch (ex: MetalPriceClientException) {
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
