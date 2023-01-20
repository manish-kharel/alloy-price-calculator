package com.aoe.alloypricecalculator.domain.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name= "currency_rate")
data class Currency(
  @Id
  val currency : String,
  val rate : Double,
)