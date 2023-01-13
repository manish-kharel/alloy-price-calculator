package com.aoe.alloypricecalculator.domain.exception

import com.aoe.alloypricecalculator.domain.model.MetalPrice

class MetalsApiFailedException(
  val prices: List<MetalPrice>,
  override val message: String
) : RuntimeException(message) {
}