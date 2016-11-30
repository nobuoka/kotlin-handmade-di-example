package info.vividcode.example.kotlin.di.handmade

import info.vividcode.example.kotlin.di.handmade.lib.di.createGraph

interface SubModule1 {
    val test1: String
    interface Requirements {
        val test2: String
        val parentComponentName: String // 親コンポーネントにより提供される値も使用できる。
    }
    class Providers(private val requirements: Lazy<Requirements>) : SubModule1 {
        override val test1: String
            get() = "Hello " + requirements.value.test2 + ", " + requirements.value.parentComponentName
    }
}

interface SubModule2 {
    val test2: String
    interface Requirements
    class Providers(private val requirements: Lazy<Requirements>) : SubModule2 {
        override val test2: String
            get() = "world"
    }
}

interface SubComponent : SubModule1, SubModule2, Component {

    companion object {
        fun create(component: Component): SubGraph = createGraph({ r -> SubGraph(r, component) })
    }

    class SubGraph(requirements: Lazy<SubGraph>, component: Component) :
            SubComponent,
            SubModule1 by SubModule1.Providers(requirements), SubModule1.Requirements,
            SubModule2 by SubModule2.Providers(requirements), SubModule2.Requirements,
            Component by component

}

/**
 * Android アプリで使う状況を想定して実装。
 */
class TestActivity(private val lazySubComponent: LazyInjection<SubComponent>) : SubComponent by lazySubComponent.proxy {

    constructor() : this(LazyInjection.create(SubComponent::class.java))

    fun onCreate(): Unit {
        val component: Component = Component.create() // 実際には Application クラスから取得する。
        lazySubComponent.inject(SubComponent.create(component))
    }

    fun onStart(): Unit {
        System.out.println(test1);
    }

}
