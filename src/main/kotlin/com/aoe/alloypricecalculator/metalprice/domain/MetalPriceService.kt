package com.aoe.alloypricecalculator.metalprice.domain

import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice
import org.springframework.stereotype.Component

@Component
interface MetalPriceService {
  fun getMetalPrices(): List<MetalPrice>
}