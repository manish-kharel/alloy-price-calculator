package com.aoe.alloypricecalculator.domain.model

class MetalPriceClientResponse(
  val metalprices: List<MetalPrice>,
  val currencyRates: List<Currency>,
)