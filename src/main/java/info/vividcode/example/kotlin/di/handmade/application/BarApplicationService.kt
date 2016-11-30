package info.vividcode.example.kotlin.di.handmade.application

import info.vividcode.example.kotlin.di.handmade.domain.BarRepository

class BarApplicationService(val name: String, val barRepository: BarRepository)
