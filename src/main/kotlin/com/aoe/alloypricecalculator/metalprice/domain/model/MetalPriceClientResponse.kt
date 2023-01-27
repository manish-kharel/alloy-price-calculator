package com.aoe.alloypricecalculator.metalprice.domain.model

import com.aoe.alloypricecalculator.metalprice.domain.model.Currency
import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice

class MetalPriceClientResponse(
  val metalprices: List<MetalPrice>,
  val currencyRates: List<Currency>,
)