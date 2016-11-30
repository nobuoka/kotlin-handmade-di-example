package info.vividcode.example.kotlin.di.handmade.infra.persistence

import info.vividcode.example.kotlin.di.handmade.domain.BarRepository
import info.vividcode.example.kotlin.di.handmade.domain.FooRepository

interface PersistenceModule {

    val fooRepository: FooRepository
    val barRepository: BarRepository

    interface Requirements

    class Providers(private val requirements: Lazy<Requirements>) : PersistenceModule {
        override val fooRepository: FooRepository
            get() = FooRepositoryImpl()
        override val barRepository: BarRepository
            get() = throw UnsupportedOperationException()
    }

}
