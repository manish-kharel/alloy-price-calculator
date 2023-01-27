package com.aoe.alloypricecalculator.exception

class ExpiredTokenException(
  override val message: String
) : RuntimeException(message) {
}