package info.vividcode.example.kotlin.di.handmade.application

import info.vividcode.example.kotlin.di.handmade.domain.BarRepository
import info.vividcode.example.kotlin.di.handmade.domain.FooRepository

/**
 * このモジュールが他のモジュール (あるいはコンポーネント) に提供するものをプロパティで列挙します。
 * 実装は [Providers] です。
 * このモジュールのインターフェイス ([ApplicationModule]) と実装 ([Providers]) の組が Dagger における Module 相当になります。
 * (Dagger の Module より素朴な感じ。)
 *
 * Dagger では実装がわかっている型については Module に書く必要はありませんでしたが、このパターンでは丁寧に
 * 書く必要があります。 (ちょっと面倒ではあるけど、Cake Pattern でもそこは一緒だしいいかなと思ってます。)
 */
interface ApplicationModule {

    val fooApplicationService: FooApplicationService

    val barApplicationService: BarApplicationService

    /**
     * [Providers] が実装を作る際に必要とするものをプロパティとして列挙します。
     * これらのプロパティは他のモジュール (どのモジュールかはわからない) で実装されることが期待されます。
     *
     * Module が必要とするものについて Dagger は自動で見つけてくれるし、Cake Pattern でも実装側にミックスインすることで依存を表現
     * するので、それらと比べるとこうやって依存を明示するのは面倒ではあるなーと思いつつ、仕組み上ないとどうしようもないので
     * 諦めて書くことにしました。
     * モジュールが何を求めているのか一覧できるので、依存関係がわかりやすくなるのは利点かなと思っています。
     */
    interface Requirements {
        val fooRepository: FooRepository
        val barRepository: BarRepository
    }

    /**
     * [ApplicationModule] で定義されているプロパティの実装を提供します。
     * コンストラクタが [Requirements] を [Lazy] で受け取るようにします。
     * この値を使って [ApplicationModule] が提供すべきオブジェクトを組み立てます。
     */
    class Providers(private val requirements: Lazy<Requirements>) : ApplicationModule {
        override val fooApplicationService: FooApplicationService
            get() = FooApplicationService(requirements.value.fooRepository)
        override val barApplicationService: BarApplicationService
            get() = BarApplicationService("test", requirements.value.barRepository)
    }

}
