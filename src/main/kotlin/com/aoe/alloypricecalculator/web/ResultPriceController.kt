package com.aoe.alloypricecalculator.web

import com.aoe.alloypricecalculator.domain.ResultPriceRepository
import com.aoe.alloypricecalculator.domain.model.ResultPrice
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class ResultPriceController(
  private val resultPriceRepository: ResultPriceRepository
) {

  @PostMapping("/createResultPrice")
  fun createResultPrice(
    @RequestBody resultPrice: ResultPrice
  ) = resultPriceRepository.save(resultPrice)

  @GetMapping("getAllSavedResults")
  fun getAllIds() = resultPriceRepository.getAllSavedResultsId()

  @GetMapping("/getCalculationSummary")
  fun getResultPrice(
    @RequestBody request: ResultPriceRequest,
  ) = resultPriceRepository.findById(request.name)

  class ResultPriceRequest(
    val name: String
  )
}

