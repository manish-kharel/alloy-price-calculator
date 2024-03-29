package com.aoe.alloypricecalculator.metalprice.domain

import com.aoe.alloypricecalculator.metalprice.domain.model.Metal
import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.transaction.Transactional

@Repository
interface MetalPriceRepository : JpaRepository<MetalPrice, Long> {

  override fun findAll(): List<MetalPrice>

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query(
    "UPDATE MetalPrice SET cost_per_unit = :costPerUnit, base_currency = :baseCurrency, " +
        "last_updated = :lastUpdated , unit = :unit WHERE name = :name"
  )
  @Transactional
  fun update(
    @Param("costPerUnit") costPerUnit: Double,
    @Param("baseCurrency") baseCurrency: String,
    @Param("lastUpdated") lastUpdated: LocalDateTime,
    @Param("unit") unit: MetalPrice.WeightUnit,
    @Param("name") name: Metal
  )
}
