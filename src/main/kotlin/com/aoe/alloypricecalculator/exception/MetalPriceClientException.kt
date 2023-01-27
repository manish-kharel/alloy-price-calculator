package com.aoe.alloypricecalculator.exception

class MetalPriceClientException(
  override val message: String
) : RuntimeException(message)