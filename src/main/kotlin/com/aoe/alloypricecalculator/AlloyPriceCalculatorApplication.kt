package com.aoe.alloypricecalculator

import com.aoe.alloypricecalculator.configuration.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class AlloyPriceCalculatorApplication

fun main(args: Array<String>) {
  runApplication<AlloyPriceCalculatorApplication>(*args)
}
