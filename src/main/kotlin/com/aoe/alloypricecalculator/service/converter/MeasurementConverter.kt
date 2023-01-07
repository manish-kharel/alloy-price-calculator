package com.aoe.alloypricecalculator.service.converter

import client.v1.MeasurementEntity
import org.springframework.stereotype.Component
import com.aoe.alloypricecalculator.domain.model.AnalysisSummary
import com.aoe.alloypricecalculator.domain.model.Measurement
import com.aoe.alloypricecalculator.domain.model.Metal

@Component
class MeasurementConverter {

  fun convertMeasurementSummary(measurementSummaries: List<MeasurementEntity.MeasurementSummary>) =
    measurementSummaries.map { measurementSummary ->
      AnalysisSummary(
        id = measurementSummary.id,
        username = measurementSummary.userName,
        profileName = measurementSummary.profileName,
        referenceName = measurementSummary.referenceName,
        measurementType = getEnumForMeasurementType(measurementSummary.measurementType.name),
        resultType = getEnumForResultType(measurementSummary.resultType.name)
      )
    }

  private fun getEnumForResultType(resultType: String): AnalysisSummary.ResultType = when (resultType) {
    "RESULT_TYPE_PASS" -> AnalysisSummary.ResultType.RESULT_TYPE_PASS
    "RESULT_TYPE_NO_MATCH" -> AnalysisSummary.ResultType.RESULT_TYPE_NO_MATCH
    else -> AnalysisSummary.ResultType.RESULT_TYPE_UNRECOGNIZED
  }

  private fun getEnumForMeasurementType(measurementType: String): AnalysisSummary.MeasurementType =
    when (measurementType) {
      "MEASUREMENT_TYPE_IDENTIFY" -> AnalysisSummary.MeasurementType.MEASUREMENT_TYPE_IDENTIFY
      "MEASUREMENT_TYPE_VERIFY" -> AnalysisSummary.MeasurementType.MEASUREMENT_TYPE_VERIFY
      else -> AnalysisSummary.MeasurementType.MEASUREMENT_TYPE_UNRECOGNIZED
    }

  fun convertMeasurementValues(measurementResponse: MeasurementEntity.MeasurementResult): Measurement =
    Measurement(
      uuid = measurementResponse.uuid,
      referenceName = measurementResponse.reading.matchesList.first().name,
      elementCompositions = getElementCompositions(measurementResponse.reading.matchesList.first().resultsList)
    )

  private fun getElementCompositions(
    resultsList: List<MeasurementEntity.Reading.PartialResult>
  ): List<Measurement.ElementComposition> = resultsList.map { result ->
    Measurement.ElementComposition(
      metal = getMetalEnum(result.name),
      value = result.value,
      standardDeviation = result.stdDev
    )
  }

  private fun getMetalEnum(name: String): Metal =
    when (name) {
      "Au" -> Metal.GOLD_24K
      "Ag" -> Metal.SILVER
      "Pt" -> Metal.PLATINUM
      "Pd" -> Metal.PALLADIUM
      "Cu" -> Metal.COPPER
      "Rh" -> Metal.RHODIUM
      "Ru" -> Metal.RUTHENIUM
      "Al" -> Metal.ALUMINUM
      "Ni" -> Metal.NICKEL
      "Zn" -> Metal.ZINC
      "Sn" -> Metal.TIN
      "Co" -> Metal.COBALT
      "Ir" -> Metal.IRIDIUM
      "Pb" -> Metal.LEAD
      "Fe" -> Metal.IRON_ORE_62PERCENT_FE
      "U" -> Metal.URANIUM
      "Mg" -> Metal.MAGNESIUM
      "Os" -> Metal.OSMIUM
      "Re" -> Metal.RHENIUM
      "In" -> Metal.INDIUM
      "Mo" -> Metal.MOLYBDENUM
      "W" -> Metal.TUNGSTEN
      "Li" -> Metal.LITHIUM
      "Sb" -> Metal.ANTIMONY
      "Ga" -> Metal.GALLIUM
      "Mn" -> Metal.MANGANESE
      "Nd" -> Metal.NEODYMIUM
      "Te" -> Metal.TELLURIUM
      "Cr" -> Metal.CHROMIUM
      "Nb" -> Metal.NEOBIUM
      "V" -> Metal.VANADIUM
      "Ti" -> Metal.TITANIUM
      else -> Metal.UNDEFINED

    }
}
