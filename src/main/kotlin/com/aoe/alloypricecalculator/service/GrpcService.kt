package com.aoe.alloypricecalculator.service

import client.v1.AuthRequestresponse
import client.v1.AuthenticationServiceGrpc
import com.aoe.alloypricecalculator.createSlf4jLogger
import io.grpc.ManagedChannel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class GrpcService(
  @Qualifier("grpcClientConfiguration")
  private val channel: ManagedChannel
) {

  val logger = createSlf4jLogger()
  fun getAuthentication() : String{
    val stub = AuthenticationServiceGrpc.newBlockingStub(channel)
    val response = stub.signin(
      AuthRequestresponse.SigninRequest.newBuilder()
        .setPassword("0000")
        .setUsername("pin")
        .build()
    )
    return response.accessToken
  }
}