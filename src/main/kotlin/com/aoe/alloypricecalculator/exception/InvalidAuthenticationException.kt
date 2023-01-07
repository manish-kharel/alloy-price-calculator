package com.aoe.alloypricecalculator.exception

import com.aoe.alloypricecalculator.domain.model.Authentication

class InvalidAuthenticationException(
  val authentication: Authentication,
  override val message :  String
) : RuntimeException(message){
}