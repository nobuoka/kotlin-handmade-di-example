package info.vividcode.example.kotlin.di.handmade.application

import info.vividcode.example.kotlin.di.handmade.domain.BarRepository

class BarApplicationService(private val requirements: Requirements) {

    interface Requirements {
        val name: String
        val barRepository: BarRepository
    }

}
