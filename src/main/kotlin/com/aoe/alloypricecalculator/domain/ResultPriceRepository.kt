package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.ResultPrice
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface ResultPriceRepository : PagingAndSortingRepository<ResultPrice, String> {
  override fun findById(id: String): Optional<ResultPrice>

  @Modifying(clearAutomatically = true)
  @Query("SELECT name from ResultPrice")
  fun getAllSavedResultsId() : List<String>
}