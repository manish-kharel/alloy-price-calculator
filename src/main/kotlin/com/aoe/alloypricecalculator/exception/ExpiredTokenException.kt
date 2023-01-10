package com.aoe.alloypricecalculator.exception

import com.aoe.alloypricecalculator.domain.model.Authentication

class ExpiredTokenException(
  override val message: String
) : RuntimeException(message) {
}