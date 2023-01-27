package com.aoe.alloypricecalculator.metalprice.domain

import com.aoe.alloypricecalculator.metalprice.domain.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional

interface CurrencyRateRepository : JpaRepository<Currency, String> {

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query(
    "UPDATE Currency SET rate = :rate WHERE currency = :currency"
  )
  @Transactional
  fun update(
    @Param("currency") currency: String,
    @Param("rate") rate: Double,
  )
}