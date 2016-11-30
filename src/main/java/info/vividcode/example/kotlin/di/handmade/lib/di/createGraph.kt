package info.vividcode.example.kotlin.di.handmade.lib.di

/**
 * オブジェクトグラフを生成する。
 */
fun <T> createGraph(constructor: (Lazy<T>) -> T): T {
    val lazyGraph: SettableLazy<T> = SettableLazy()
    val g = constructor(lazyGraph)
    lazyGraph.setValue(g)
    return g
}
