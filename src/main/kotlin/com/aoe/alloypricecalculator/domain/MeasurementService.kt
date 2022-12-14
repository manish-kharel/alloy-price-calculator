package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.AnalysisSummary
import com.aoe.alloypricecalculator.domain.model.Authentication
import com.aoe.alloypricecalculator.domain.model.Measurement
import io.grpc.Metadata
import org.springframework.stereotype.Component

@Component
interface MeasurementService {
  fun checkAuthentication(): Authentication
  fun getMeasurementList(authentication: Authentication): List<AnalysisSummary>
  fun getMeasurementValues(authentication: Authentication, measurement_uuid: String): Measurement
}