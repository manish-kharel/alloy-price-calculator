package com.aoe.alloypricecalculator.metalprice.infrastructure.converter

import com.aoe.alloypricecalculator.metalprice.domain.model.Currency
import com.aoe.alloypricecalculator.metalprice.domain.model.Metal
import com.aoe.alloypricecalculator.metalprice.domain.model.MetalPrice
import org.json.JSONObject
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class MetalPriceConverter {

  fun convertMetalPrices(response: String?) = convertElement(JSONObject(response))

  private fun convertElement(jsonObject: JSONObject): List<MetalPrice> =
    getNameAndCostMap(jsonObject.getJSONObject("rates"))?.mapNotNull { (name, rate) ->
      if (filterJsonForMetalOnly(name)) {
        MetalPrice(
          name = getMetalEnum(name),
          costPerUnit = 1 / rate,
          lastUpdated = getDateTimeFromTimeStamp(jsonObject.getLong("timestamp")),
//          lastUpdated = LocalDateTime.now(),
          baseCurrency = jsonObject.getString("base"),

          unit = convertUnitToEnum(jsonObject.getString("unit"))
        )
      } else
        null
    } ?: emptyList()


  fun convertCurrencyRates(response: String) = JSONObject(response).getJSONObject("rates").let { ratesJson ->
    getNameAndCostMap(ratesJson)
  }?.mapNotNull { (name, rate) ->
    if (!filterJsonForMetalOnly(name) && !filterUnusedMetalEntities(name)) {
      Currency(
        name,
        rate
      )
    } else
      null
  } ?: emptyList()

  private fun filterUnusedMetalEntities(name: String): Boolean = (
      name == "LBXAUAM" || name == "LBXAUPM" || name == "LBXAG" || name == "LBXPTAM" || name == "LBXPTPM" || name == "LBXPDAM"
          || name == "LBXPDPM" || name == "LME-ALU" || name == "LME-XCU" || name == "LME-ZNC" || name == "LME-NI" || name == "LME-LEAD"
          || name == "LME-TIN" || name == "STEEL-SC" || name == "STEEL-RE" || name == "STEEL-HR" || name == "XAU-AHME" || name == "XAU-BANG"
          || name == "XAU-CHEN" || name == "XAU-COIM" || name == "XAU-DELH" || name == "XAU-HYDE" || name == "XAU-KOCH" || name == "XAU-KOLK"
          || name == "XAU-MUMB" || name == "XAU-SURA" || name == "XAG-AHME" || name == "XAG-BANG"
          || name == "XAG-CHEN" || name == "XAG-COIM" || name == "XAG-DELH" || name == "XAG-HYDE" || name == "XAG-KOCH" || name == "XAG-KOLK"
          || name == "XAG-MUMB" || name == "XAG-SURA"
      )

  private fun filterJsonForMetalOnly(name: String): Boolean =
    name == "XAU" || name == "XAG" || name == "XPT" || name == "XPD" || name == "XCU" || name == "XRH" ||
        name == "RUTH" || name == "ALU" || name == "NI" || name == "ZNC" || name == "TIN" || name == "LCO" ||
        name == "IRD" || name == "LEAD" || name == "IRON" || name == "URANIUM" || name == "BRONZE" || name == "MG" ||
        name == "OSMIUM" || name == "RHENIUM" || name == "INDIUM" || name == "MO" || name == "TUNGSTEN" ||
        name == "LITHIUM" || name == "ANTIMONY" || name == "BITUMEN" || name == "GALLIUM" || name == "MN" ||
        name == "ND" || name == "TE"

  private fun getMetalEnum(name: String): Metal = when (name) {
    "XAU" -> Metal.GOLD_24K
    "XAG" -> Metal.SILVER
    "XPT" -> Metal.PLATINUM
    "XPD" -> Metal.PALLADIUM
    "XCU" -> Metal.COPPER
    "XRH" -> Metal.RHODIUM
    "RUTH" -> Metal.RUTHENIUM
    "ALU" -> Metal.ALUMINUM
    "NI" -> Metal.NICKEL
    "ZNC" -> Metal.ZINC
    "TIN" -> Metal.TIN
    "LCO" -> Metal.COBALT
    "IRD" -> Metal.IRIDIUM
    "LEAD" -> Metal.LEAD
    "IRON" -> Metal.IRON_ORE_62PERCENT_FE
    "URANIUM" -> Metal.URANIUM
    "BRONZE" -> Metal.BRONZE
    "MG" -> Metal.MAGNESIUM
    "OSMIUM" -> Metal.OSMIUM
    "RHENIUM" -> Metal.RHENIUM
    "INDIUM" -> Metal.INDIUM
    "MO" -> Metal.MOLYBDENUM
    "TUNGSTEN" -> Metal.TUNGSTEN
    "LITHIUM" -> Metal.LITHIUM
    "ANTIMONY" -> Metal.ANTIMONY
    "BITUMEN" -> Metal.BITUMEN
    "GALLIUM" -> Metal.GALLIUM
    "MN" -> Metal.MANGANESE
    "ND" -> Metal.NEODYMIUM
    "TE" -> Metal.TELLURIUM
    else -> Metal.UNDEFINED
  }

  private fun getNameAndCostMap(jsonObject: JSONObject?) =
    jsonObject?.toMap()?.keys?.associate { key -> key to jsonObject.getDouble(key) }

  private fun getDateTimeFromTimeStamp(timestamp: Long): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()
  )

  private fun convertUnitToEnum(unit: String?): MetalPrice.WeightUnit = when (unit) {
    "per ounce" -> MetalPrice.WeightUnit.PER_OUNCE
    "gram" -> MetalPrice.WeightUnit.PER_GRAM
    else -> MetalPrice.WeightUnit.NOT_SPECIFIED     // TODO  add others
  }
}