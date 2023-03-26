package com.aoe.alloypricecalculator.resultprice.web

import com.aoe.alloypricecalculator.resultprice.domain.ResultPriceRepository
import com.aoe.alloypricecalculator.resultprice.domain.model.ResultPrice
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
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
    @RequestParam name: String,
  ) = resultPriceRepository.findById(name)

  @DeleteMapping("deletePrice")
  fun deletePrice(
    @RequestParam name: String,
  ) = resultPriceRepository.deleteById(name)
}

