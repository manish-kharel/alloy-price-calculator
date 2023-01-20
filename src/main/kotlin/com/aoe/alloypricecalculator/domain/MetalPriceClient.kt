package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.MetalPrice
import com.aoe.alloypricecalculator.domain.model.MetalPriceClientResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
interface MetalPriceClient {
  fun getMetalPrices(): MetalPriceClientResponse
}