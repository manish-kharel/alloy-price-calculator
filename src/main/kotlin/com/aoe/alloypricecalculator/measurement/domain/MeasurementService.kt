package com.aoe.alloypricecalculator.measurement.domain

import com.aoe.alloypricecalculator.measurement.domain.model.AnalysisSummary
import com.aoe.alloypricecalculator.authentication.domain.model.Authentication
import com.aoe.alloypricecalculator.measurement.domain.model.Measurement
import org.springframework.stereotype.Component

@Component
interface MeasurementService {
  fun getAnalysisSummaries(authentication: Authentication): List<AnalysisSummary>
  fun getMeasurementValues(authentication: Authentication, measurement_uuid: String): List<Measurement>
}