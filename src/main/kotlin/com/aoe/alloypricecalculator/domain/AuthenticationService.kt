package com.aoe.alloypricecalculator.domain

import com.aoe.alloypricecalculator.domain.model.Authentication
import com.aoe.alloypricecalculator.domain.model.GrpcUser
import org.springframework.stereotype.Component

@Component
interface AuthenticationService {
  fun getAuthentication(grpcUser: GrpcUser): Authentication
}