package com.aoe.alloypricecalculator.configuration

import org.jetbrains.annotations.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application")
class ApplicationProperties {

  val mockApi = MockApiProperties()

  class MockApiProperties {

    @NotNull
    lateinit var url : String
  }
}