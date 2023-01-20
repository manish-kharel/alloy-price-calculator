package com.aoe.alloypricecalculator.domain.exception

class MetalPriceClientException(
  override val message: String
) : RuntimeException(message)