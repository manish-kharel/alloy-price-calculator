package com.aoe.alloypricecalculator.configuration

import io.grpc.ManagedChannelBuilder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class GrpcClientConfiguration {

  @Bean("grpcClientConfiguration")
  fun grpcClient() = ManagedChannelBuilder.forAddress("localhost", 3001)
    .usePlaintext().build()
}
