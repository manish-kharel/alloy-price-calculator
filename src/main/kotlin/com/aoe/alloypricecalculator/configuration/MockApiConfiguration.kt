package com.aoe.alloypricecalculator.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class MockApiConfiguration(
  val applicationProperties: ApplicationProperties
) {

  @Bean("mockApiRestTemplate")
  fun customMockApi(): RestTemplate = applicationProperties.mockApi.url.let { url ->
    RestTemplateBuilder().rootUri(url)
      .build()
  }
}