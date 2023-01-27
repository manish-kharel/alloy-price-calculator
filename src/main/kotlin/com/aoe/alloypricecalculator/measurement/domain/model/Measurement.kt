package com.aoe.alloypricecalculator.measurement.domain.model

import com.aoe.alloypricecalculator.metalprice.domain.model.Metal

data class Measurement(
  val uuid : String,
  val referenceName : String,
  val elementCompositions: List<ElementComposition>,
) {
  data class ElementComposition(
    val metal: Metal,
    val value: Double,
    val standardDeviation: Double
  )
}
