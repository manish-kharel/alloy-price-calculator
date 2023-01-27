package com.aoe.alloypricecalculator.resultprice.domain.model

import com.aoe.alloypricecalculator.metalprice.domain.model.Metal
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "result_price")
data class ResultPrice(

  @Id
  val name: String,
  val sampleWeight : Double,

  @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, targetEntity = ElementValue::class)
  @JoinColumn(name = "rs_fk", referencedColumnName = "name")
  val elementValues: List<ElementValue>,

  ) {
  @Entity
  data class ElementValue(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val eid: Int,

    @Enumerated(EnumType.STRING)
    val metal: Metal,
    val value: Double,
    val standardDeviation: Double,
    val equivalentWeight: Double,
    val equivalentPrice: Double,
  )
}