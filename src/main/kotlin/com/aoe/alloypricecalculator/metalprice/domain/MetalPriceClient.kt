package com.aoe.alloypricecalculator.metalprice.domain

import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPriceClientResponse
import org.springframework.stereotype.Component

@Component
interface MetalPriceClient {
  fun getMetalPrices(): MetalPriceClientResponse
}