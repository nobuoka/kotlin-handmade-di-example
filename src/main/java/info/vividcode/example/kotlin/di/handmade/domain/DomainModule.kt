package info.vividcode.example.kotlin.di.handmade.domain

interface DomainModule {

    interface Requirements

    class Providers(requirements: Lazy<Requirements>) : DomainModule

}
