package com.aoe.alloypricecalculator.service.converter

import com.aoe.alloypricecalculator.domain.model.Metal
import com.aoe.alloypricecalculator.domain.model.MetalPrice
import org.json.JSONObject
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class MetalPriceConverter {

  fun convert(response: String?) = convertElement(JSONObject(response))

  private fun convertElement(jsonObject: JSONObject): List<MetalPrice> =
    getNameAndCostMap(jsonObject.getJSONObject("rates"))?.mapNotNull { (name, rate) ->
      if (filterJsonForMetalOnly(name)) {
        MetalPrice(
          name = getMetalEnum(name),
          costPerUnit = 1 / rate,
//          lastUpdated = getDateTimeFromTimeStamp(jsonObject.getLong("timestamp")),
          lastUpdated = LocalDateTime.now(),
          baseCurrency = jsonObject.getString("base"),

          unit = convertUnitToEnum(jsonObject.getString("unit"))
        )
      } else
        null
    } ?: emptyList()

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