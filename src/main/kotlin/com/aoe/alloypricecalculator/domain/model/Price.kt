package com.aoe.alloypricecalculator.domain.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "prices")
class Price (

  val lastUpdated : LocalDateTime,
  val baseCurrency : String,    // change to enum

  @Enumerated(EnumType.STRING)
  val unit : WeightUnit,

  @OneToMany(mappedBy= "price", fetch = FetchType.LAZY, cascade = [CascadeType.ALL] )
  val elements : List<Element>,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,
    ) {

  enum class WeightUnit {
    PER_OUNCE,
    PER_GRAM,
    NOT_SPECIFIED,
  }
}