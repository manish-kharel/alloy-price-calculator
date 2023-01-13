package com.aoe.alloypricecalculator.domain.exception

class ExpiredTokenException(
  override val message: String
) : RuntimeException(message) {
}