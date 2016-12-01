package info.vividcode.example.kotlin.di.handmade

import info.vividcode.example.kotlin.di.handmade.application.ApplicationModule
import info.vividcode.example.kotlin.di.handmade.domain.DomainModule
import info.vividcode.example.kotlin.di.handmade.infra.persistence.PersistenceModule
import info.vividcode.example.kotlin.di.handmade.lib.di.createGraph

/**
 * Component に各 Module を継承させるかどうかは悩みどころ。
 */
interface Component : ApplicationModule, DomainModule, PersistenceModule {

    // 各モジュールが提供する値以外にコンポーネント自身が提供する値を宣言することもできる。 (できなくても良さそう。)
    val parentComponentName: String

    companion object {
        fun create(): Graph = createGraph(::Graph)
    }

    /**
     * [Component] の実装。
     * モジュールを組み合わせて 1 つのコンポーネントを生成する。
     */
    class Graph(requires: Lazy<Graph>) :
            Component,
            ApplicationModule by ApplicationModule.Providers(requires), ApplicationModule.Requirements,
            DomainModule by DomainModule.Providers(requires), DomainModule.Requirements,
            PersistenceModule by PersistenceModule.Providers(requires), PersistenceModule.Requirements {
        override val name: String
            get() = "名前だよ"
        override val parentComponentName: String
            get() = "親コンポーネント (" + fooRepository.name + ")"
    }

}
