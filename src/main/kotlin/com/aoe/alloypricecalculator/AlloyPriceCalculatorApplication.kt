package com.aoe.alloypricecalculator

import client.v1.AuthRequestresponse
import client.v1.AuthRequestresponse.SigninRequest
import client.v1.AuthRequestresponse.SigninResponse
import client.v1.AuthenticationServiceGrpc
import com.aoe.alloypricecalculator.configuration.ApplicationProperties
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import io.grpc.stub.StreamObservers
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import kotlin.io.path.createTempDirectory

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class AlloyPriceCalculatorApplication

fun main(args: Array<String>) {
  runApplication<AlloyPriceCalculatorApplication>(*args)
}
