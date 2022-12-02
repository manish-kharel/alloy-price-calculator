package com.aoe.alloypricecalculator.converter

import com.aoe.alloypricecalculator.domain.model.Element
import com.aoe.alloypricecalculator.domain.model.Price
import org.json.JSONObject
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class PriceConverter{
  fun convert(response: String?) = convertPrice(JSONObject(response))

  private fun convertPrice(jsonObject: JSONObject) = Price(
    lastUpdated = getDateTimeFromTimeStamp(jsonObject.getLong("timestamp")),
    baseCurrency = jsonObject.getString("base"),
    unit = convertUnitToEnum(jsonObject.getString("unit")),
    elements = convertElement(jsonObject.getJSONObject("rates"))
  )

  private fun getDateTimeFromTimeStamp(timestamp: Long): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()
  )

  private fun convertElement(jsonObject: JSONObject?): List<Element> =
    jsonObject?.toMap()?.keys?.associate { key -> key to jsonObject.getFloat(key) }?.mapNotNull { (name, rate) ->
      if (filterJsonForMetalOnly(name)) {
        Element(
          name = getMetalEnum(name),
          costPerUnit = 1 / rate
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

  private fun getMetalEnum(name: String): Element.Metal = when (name) {
    "XAU" -> Element.Metal.GOLD_24K
    "XAG" -> Element.Metal.SILVER
    "XPT" -> Element.Metal.PLATINUM
    "XPD" -> Element.Metal.PALLADIUM
    "XCU" -> Element.Metal.COPPER
    "XRH" -> Element.Metal.RHODIUM
    "RUTH" -> Element.Metal.RUTHENIUM
    "ALU" -> Element.Metal.ALUMINUM
    "NI" -> Element.Metal.NICKEL
    "ZNC" -> Element.Metal.ZINC
    "TIN" -> Element.Metal.TIN
    "LCO" -> Element.Metal.COBALT
    "IRD" -> Element.Metal.IRIDIUM
    "LEAD" -> Element.Metal.LEAD
    "IRON" -> Element.Metal.IRON_ORE_62PERCENT_FE
    "URANIUM" -> Element.Metal.URANIUM
    "BRONZE" -> Element.Metal.BRONZE
    "MG" -> Element.Metal.MAGNESIUM
    "OSMIUM" -> Element.Metal.OSMIUM
    "RHENIUM" -> Element.Metal.RHENIUM
    "INDIUM" -> Element.Metal.INDIUM
    "MO" -> Element.Metal.MOLYBDENUM
    "TUNGSTEN" -> Element.Metal.TUNGSTEM
    "LITHIUM" -> Element.Metal.LITHIUM
    "ANTIMONY" -> Element.Metal.ANTIMONY
    "BITUMEN" -> Element.Metal.BITUMEN
    "GALLIUM" -> Element.Metal.GALLIUM
    "MN" -> Element.Metal.MANGANESE
    "ND" -> Element.Metal.NEODYMIUM
    "TE" -> Element.Metal.TELLURIUM
    else -> Element.Metal.UNDEFINED
  }

  private fun convertUnitToEnum(unit: String?): Price.WeightUnit = when (unit) {
    "per ounce" -> Price.WeightUnit.PER_OUNCE
    "gram" -> Price.WeightUnit.PER_GRAM
    else -> Price.WeightUnit.NOT_SPECIFIED     // TODO  add others
  }
}