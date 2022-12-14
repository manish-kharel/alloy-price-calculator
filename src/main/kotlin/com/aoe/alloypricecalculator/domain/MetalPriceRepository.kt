package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.MetalPrice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MetalPriceRepository : PagingAndSortingRepository<MetalPrice, Long>
