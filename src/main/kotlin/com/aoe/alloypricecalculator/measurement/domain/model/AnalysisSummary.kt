package com.aoe.alloypricecalculator.measurement.domain.model

data class AnalysisSummary(
  val username: String,
  val profileName: String,
  val id: String,
  val referenceName: String,
  val resultType: ResultType,
  val measurementType: MeasurementType
) {
  enum class ResultType {
    RESULT_TYPE_NO_MATCH,
    RESULT_TYPE_PASS,
    RESULT_TYPE_UNRECOGNIZED
  }

  enum class MeasurementType {
    MEASUREMENT_TYPE_IDENTIFY,
    MEASUREMENT_TYPE_VERIFY,
    MEASUREMENT_TYPE_UNRECOGNIZED
  }
}
