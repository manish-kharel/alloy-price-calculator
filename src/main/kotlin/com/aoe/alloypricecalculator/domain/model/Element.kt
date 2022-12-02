package com.aoe.alloypricecalculator.domain.model

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "elements")
class Element(

 @Enumerated(EnumType.STRING)
 val name: Metal,
 val costPerUnit : Float,

 @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name= "price_id")
 val price: Price? = null,

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 val id: Long? = null,
) {

 enum class Metal {
  GOLD_24K,
  SILVER,
  PLATINUM,
  PALLADIUM,
  COPPER,
  RHODIUM,
  RUTHENIUM,
  ALUMINUM,
  NICKEL,
  ZINC,
  TIN,
  COBALT,
  IRIDIUM,
  LEAD,
  IRON_ORE_62PERCENT_FE,
  URANIUM,
  BRONZE,
  MAGNESIUM,
  OSMIUM,
  RHENIUM,
  INDIUM,
  MOLYBDENUM,
  TUNGSTEM,
  LITHIUM,
  ANTIMONY,
  BITUMEN,
  GALLIUM,
  MANGANESE,
  NEODYMIUM,
  TELLURIUM,
  UNDEFINED
 }
}
