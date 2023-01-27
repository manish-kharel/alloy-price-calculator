package com.aoe.alloypricecalculator.exception

import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice

class MetalsApiFailedException(
  val prices: List<MetalPrice>,
  override val message: String
) : RuntimeException(message) {
}