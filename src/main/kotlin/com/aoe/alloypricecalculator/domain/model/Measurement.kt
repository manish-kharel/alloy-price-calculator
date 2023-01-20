package com.aoe.alloypricecalculator.domain.model

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
