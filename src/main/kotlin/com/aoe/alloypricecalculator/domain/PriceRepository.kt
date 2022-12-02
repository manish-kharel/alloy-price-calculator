package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.Price
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceRepository : PagingAndSortingRepository<Price, Long>
