package info.vividcode.example.kotlin.di.handmade.domain

interface FooRepository {
    val name: String
        get() = "Hello!"
}
