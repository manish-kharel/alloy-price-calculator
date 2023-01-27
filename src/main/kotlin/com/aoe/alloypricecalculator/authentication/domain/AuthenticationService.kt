package com.aoe.alloypricecalculator.authentication.domain

import com.aoe.alloypricecalculator.authentication.domain.model.Authentication
import com.aoe.alloypricecalculator.authentication.domain.model.GrpcUser
import org.springframework.stereotype.Component

@Component
interface AuthenticationService {
  fun getAuthentication(grpcUser: GrpcUser): Authentication
}