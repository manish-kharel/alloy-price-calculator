package com.aoe.alloypricecalculator.metalprice.domain.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "metal_prices")
class MetalPrice(

 @Enumerated(EnumType.STRING)
 val name: Metal,
 @Enumerated(EnumType.STRING)
 val unit : WeightUnit,
 val costPerUnit : Double,
 val lastUpdated : LocalDateTime,
 val baseCurrency : String,    // change to enum

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 val id: Long? = null,
) {

 enum class WeightUnit {
  PER_OUNCE,
  PER_GRAM,
  NOT_SPECIFIED,
 }
}