package com.aoe.alloypricecalculator.service

import client.v1.AuthRequestresponse
import client.v1.AuthenticationServiceGrpc
import client.v1.MeasurementRequestresponse
import client.v1.MeasurementServiceGrpc
import com.aoe.alloypricecalculator.service.converter.MeasurementConverter
import io.grpc.Metadata
import com.aoe.alloypricecalculator.createSlf4jLogger
import com.aoe.alloypricecalculator.domain.MeasurementService
import com.aoe.alloypricecalculator.domain.model.AnalysisSummary
import com.aoe.alloypricecalculator.domain.model.Authentication
import com.aoe.alloypricecalculator.domain.model.GrpcUser
import com.aoe.alloypricecalculator.domain.model.Measurement
import com.aoe.alloypricecalculator.exception.ExpiredTokenException
import com.aoe.alloypricecalculator.exception.InvalidAuthenticationException
import io.grpc.ManagedChannel
import io.grpc.StatusRuntimeException
import io.grpc.stub.MetadataUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class MeasurementServiceImpl(
  @Qualifier("grpcClient")
  private val channel: ManagedChannel,
  private val measurementConverter: MeasurementConverter,
) : MeasurementService {

  val logger = createSlf4jLogger()
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

  override fun getMeasurementList(authentication: Authentication): List<AnalysisSummary> = try {
    val metadata = setMetadata(authentication)

    MeasurementServiceGrpc.newBlockingStub(channel).run {
      this.withInterceptors(
        MetadataUtils.newAttachHeadersInterceptor(metadata)
      )
    }.listMeasurements(
      MeasurementRequestresponse.ListMeasurementsRequest.newBuilder()
        .setStartReference("")
        .setResultCount(100)
        .build()
    ).let { response ->
      measurementConverter.convertMeasurementSummary(response.measurementsList)
    }
  } catch (ex: StatusRuntimeException) {
    throw ExpiredTokenException("The user token has expired")
  }

  override fun getMeasurementValues(authentication: Authentication, measurement_uuid: String) = try {
    val metadata = setMetadata(authentication)

    MeasurementServiceGrpc.newBlockingStub(channel).run {
      this.withInterceptors(
        MetadataUtils.newAttachHeadersInterceptor(metadata)
      )
    }.getMeasurement(
      MeasurementRequestresponse.GetMeasurementRequest.newBuilder()
        .setUuid(measurement_uuid)
        .build()
    ).let { measurementResponse ->
      listOf(measurementConverter.convertMeasurementValues(measurementResponse.measurement))
    }
  } catch (ex: StatusRuntimeException) {
    throw ExpiredTokenException(" ${ex.message}").also {
      logger.info(ex.message)
    }
  }

  private fun setMetadata(authentication: Authentication) = Metadata().apply {
    this.put(
      Metadata.Key.of(
        "Authorization", Metadata.ASCII_STRING_MARSHALLER
      ), "bearer " + authentication.token
    )
  }
}