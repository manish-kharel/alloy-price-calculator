package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.MetalPrice
import org.springframework.stereotype.Component

@Component
interface MetalPriceService {
  fun getMetalPrices(): List<MetalPrice>?
}