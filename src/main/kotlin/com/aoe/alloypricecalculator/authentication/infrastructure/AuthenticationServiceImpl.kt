package com.aoe.alloypricecalculator.authentication.infrastructure

import client.v1.AuthRequestresponse
import client.v1.AuthenticationServiceGrpc
import com.aoe.alloypricecalculator.authentication.domain.AuthenticationService
import com.aoe.alloypricecalculator.authentication.domain.model.Authentication
import com.aoe.alloypricecalculator.authentication.domain.model.GrpcUser
import io.grpc.ManagedChannel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class AuthenticationServiceImpl(
  @Qualifier("grpcClient")
  private val channel: ManagedChannel,
) : AuthenticationService {

  override fun getAuthentication(grpcUser: GrpcUser) = AuthenticationServiceGrpc.newBlockingStub(channel).let { stub ->
    stub.signin(
      AuthRequestresponse.SigninRequest.newBuilder()
        .setUsername(grpcUser.username)
        .setPassword(grpcUser.password)
        .build()
    )
  }.let { response ->
    Authentication(
      token = response.accessToken
    )
  }
}